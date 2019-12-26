#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys 
import os 
import rpy2.robjects as robjects
import matplotlib.pyplot as plt
import numpy as np
import ConfigParser

reload(sys)  
sys.setdefaultencoding('utf8')

filename = '../../config-files/config.properties'

config = ConfigParser.RawConfigParser()
config.read(filename)

list_num_hosts_per_nfvipop = config.get('DEFAULT', 'num_hosts_per_nfvipop').split(",")
list_num_nfv_services = config.get('DEFAULT', 'num_nfv_services').split(",")
list_num_vnfcs = config.get('DEFAULT', 'num_vnfcs').split(",")
rounds = config.get('DEFAULT','rounds')

#print(num_hosts_per_nfvipop)
#print(num_nfv_services)
#print(num_vnfcs)
#print(rounds)

r = robjects.r
r.library("nortest")
r.library("MASS")

r('''
        wilcox.onesample.test <- function(v, verbose=FALSE) {
           wilcox.test(v,mu=median(v),conf.int=TRUE, conf.level = 0.95)
        }
        wilcox.twosamples.test <- function(v, r, verbose=FALSE) {
           wilcox.test(v,r)
        }        
        ''')

# Normality Test
lillie = robjects.r('lillie.test') # Lilliefors

# Close pdf graphics
close_pdf = robjects.r('dev.off') 

# Non-parametric Tes
wilcoxon_test_two_samples = robjects.r['wilcox.twosamples.test']
wilcoxon_test_one_sample = robjects.r['wilcox.onesample.test']

confidence_interval = 0.05

# Close pdf graphics
close_pdf = robjects.r('dev.off') 

os.system("rm -rf ../figures ../norm-tests ../np-tests")
os.system("mkdir -p ../figures/eda ../figures/charts ../norm-tests ../np-tests")

stats = {}
topologies = []

for num_hosts_per_nfvipop in list_num_hosts_per_nfvipop:
	for num_nfv_services in list_num_nfv_services:		
		file = open("../result-"+num_hosts_per_nfvipop+"-"+num_nfv_services+".txt", "r")
		topology = ""
		num_vnfcs_in_sfc_request = ""
		for line in file: 
			if "BEGIN" in line:
				topology = line.split(":")[1].split(" ")[1].strip()								
				if topology not in stats.keys():
					stats[topology] = {}
					topologies.append(topology)
				stats[topology][num_hosts_per_nfvipop] = {}
				stats[topology][num_hosts_per_nfvipop][num_nfv_services] = {}
				continue
			if "File Size" in line:
				stats[topology][num_hosts_per_nfvipop][num_nfv_services]["file_size"] = int(line.split(":")[1].strip())
			if "Total VNFs" in line:
				num_vnfcs_in_sfc_request = line.split(":")[1].strip()
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request] = []
				continue
			if "consistency" in line:
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request] = {}

				values = [float(s.strip())/1000 for s in line.split("[")[1].split("]")[0].split(",")]				
				xvalues = robjects.FloatVector(values)
				
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["sample"] = xvalues
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["median"] = r.median(xvalues)[0]
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["mean"] = r.mean(xvalues)[0]
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["sd"] = r.sd(xvalues)[0]
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["min"] = r.min(xvalues)[0]
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["max"] = r.max(xvalues)[0]
				
				test_wilcoxon = wilcoxon_test_one_sample(xvalues)							
				error_max = test_wilcoxon[7][1]		
				median = test_wilcoxon[8][0]

				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["wilcoxon_test_median"] = median
				stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["wilcoxon_test_error"] = float(error_max)-float(median)

				#print stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["wilcoxon_test_median"]
				#print stats[topology][num_hosts_per_nfvipop][num_nfv_services][num_vnfcs_in_sfc_request]["wilcoxon_test_error"]

				title_graph = topology+"-"+num_hosts_per_nfvipop+"-"+num_nfv_services+"-"+num_vnfcs_in_sfc_request
				xlabel = "Time to Consistency Test (ms)"
				# Histogram
				r.pdf("../figures/eda/hist-"+topology+"-"+num_hosts_per_nfvipop+"-"+num_nfv_services+"-"+num_vnfcs_in_sfc_request+".pdf")
	 			r.hist(xvalues, main = title_graph, col="blue", xlab = xlabel, ylab = "Absolute Frequency")
	 			close_pdf()
	 			# Boxplots
	 			r.pdf("../figures/eda/box-"+topology+"-"+num_hosts_per_nfvipop+"-"+num_nfv_services+"-"+num_vnfcs_in_sfc_request+".pdf")
	 			r.boxplot(xvalues, main = title_graph,col="lightblue", horizontal=True, las=1, xlab=xlabel)
	 			close_pdf()
		file.close()

######################################################################################

fig_num=0
colors = ["b","g","r"]
markers = ["o","^","s"]
topologies.sort()

figures_y = ["time","size"]
figures_x = ["nodes","vnfcs"]
figures_type = ["bar","line"]


######################################################################################

# FIGURES

for figure_y in figures_y:
	for figure_x in figures_x:
		for figure_type in figures_type:
			fig_num+=1
			plt.figure(fig_num)
			
			if (figure_type == "bar"):
				width = 0.25       # the width of the bars	
				opacity = 0.5
			
			if (figure_y == "time"):
				error_config = {'ecolor': 'black'}

			if (figure_type == "bar"):
				ind = np.arange(len(topologies))
			elif (figure_x == "nodes"):
				ind = np.arange(len(list_num_hosts_per_nfvipop))
			elif (figure_x == "vnfcs"):
				ind = np.arange(len(list_num_vnfcs))

			fig, ax = plt.subplots(sharex=False, sharey=False)
			charts = []

			if (figure_type == "bar"):
				x = ind - width
			
			ind_color = 0
			ind_marker = 0			

			for topology in topologies:	
				if (figure_y == "time"):
					vectors_median = {"values":[],"errors":[]}
				else:
					vectors_files_size = []

				elements = []
				if (figure_x == "nodes"):
					elements = list_num_hosts_per_nfvipop
				else:
					elements = list_num_vnfcs

				for element in elements:
					if (figure_y == "time"):
						if (figure_x == "nodes"):
							median = stats[topology][element]["1"]["5"]["wilcoxon_test_median"] 
							error = stats[topology][element]["1"]["5"]["wilcoxon_test_error"]							
						else:
							median = stats[topology]["10"]["1"][element]["wilcoxon_test_median"] 
							error = stats[topology]["10"]["1"][element]["wilcoxon_test_error"]
						vectors_median["values"].append(median)
						vectors_median["errors"].append(error)
					else:
						if (figure_x == "nodes"):
							vectors_files_size.append(float(stats[topology][element]["1"]["file_size"])/1000000)
						else: 
							vectors_files_size.append(float(stats[topology]["10"]["1"]["file_size"])/1000000)

				if (figure_type == "bar"):
					if (figure_y == "time"):
						barChart = ax.bar(x, vectors_median["values"], width, alpha=opacity, color=colors[ind_color],yerr=vectors_median["errors"],error_kw=error_config) #yerr=menStd 
					else:
						barChart = ax.bar(x, vectors_files_size, width, alpha=opacity, color=colors[ind_color]) #yerr=menStd 
					charts.append(barChart)
					x = x + width
				else: 
					if (figure_y == "time"):
						lineChart = ax.plot(ind, vectors_median["values"], color=colors[ind_color], marker=markers[ind_marker], markersize=10)
						ax.errorbar(ind, vectors_median["values"], color=colors[ind_color], yerr=vectors_median["errors"])					
					else: 
						lineChart = ax.plot(ind, vectors_files_size, color=colors[ind_color], marker=markers[ind_marker], markersize=10)
					charts.append(lineChart)
			
				ind_color+=1
				ind_marker+=1

			if (figure_type == "bar"):
				ax.set_xticks(ind + width/2)
			else:
				ax.set_xticks(ind)
			
			if (figure_x == "nodes"): 
				ax.set_xticklabels(list_num_hosts_per_nfvipop,fontsize=20)
			else:
				ax.set_xticklabels(list_num_vnfcs,fontsize=20)

			ax.legend((charts[0][0], charts[1][0], charts[2][0]), (topologies[0], topologies[1], topologies[2]),bbox_to_anchor=(0., 1.02, 1., .102), loc=3,ncol=3, mode="expand", borderaxespad=0.)

			ax.tick_params(labelsize=20)
			ax.grid(True)

			if (figure_x == "nodes"):
				xlabel = "Number of NFVI-Nodes per NFVI-PoP"
			else:
				xlabel = "Number of VNFCs in SFC Request"

			if (figure_y == "time"):
				ylabel = "Consistency Check Time (s)"
			else:
				ylabel = "Ontology File Size (MB)"
			
			fig.text(0.5, 0.01, xlabel, ha='center', fontsize=20)
			fig.text(0.01, 0.5, ylabel, va='center', rotation='vertical', fontsize=20)

			plt.savefig("../figures/charts/"+figure_type+"-"+figure_y+"-"+figure_x+".pdf",format='pdf')