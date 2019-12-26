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

list_num_thresholds = config.get('DEFAULT', 'num_thresholds').split(",")
list_num_goals = config.get('DEFAULT', 'num_goals').split(",")
rounds = config.get('DEFAULT','rounds')

fixed_level = "100"
metrics = ["consistency","planning","search","plancon"]
metrics_title = {"consistency":"Time to Consistency Before (s)","planning":"Time to Planning (s)","search":"Time to Search Plan (s)","plancon":"Time to Consistency After (s)"}

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

# For Linux
#os.system("rm -rf ../figures ../norm-tests ../np-tests")
#os.system("mkdir -d ../figures/eda ../figures/charts ../norm-tests ../np-tests"')

# For Windows
os.system('cmd /c "rd /s /q ..\\figures ..\\norm-tests ..\\np-tests"')
os.system('cmd /c "mkdir ..\\figures\\eda ..\\figures\\charts ..\\norm-tests ..\\np-tests"')

stats = {}
topologies = []

for num_thresholds in list_num_thresholds:
	for num_goals in list_num_goals:		
		file = open("../result-"+num_thresholds+"-"+num_goals+".txt", "r")
		for line in file: 
			if "BEGIN" in line:
				topology = line.split(":")[1].split(" ")[1].strip()								
				if topology not in stats.keys():
					stats[topology] = {}
					topologies.append(topology)
				if (num_thresholds not in stats[topology].keys()):
					stats[topology][num_thresholds] = {}
				stats[topology][num_thresholds][num_goals] = {}
				continue
			if "File Size" in line:
				stats[topology][num_thresholds][num_goals]["file_size"] = int(line.split(":")[1].strip())
				continue
			for metric in metrics:
				if metric in line:
					stats[topology][num_thresholds][num_goals][metric] = {}

					if (metric == "search"):
						values = [float(s.strip()) for s in line.split("[")[1].split("]")[0].split(",")]				
					else:
						values = [float(s.strip())/1000 for s in line.split("[")[1].split("]")[0].split(",")]				
					
					xvalues = robjects.FloatVector(values)
					
					stats[topology][num_thresholds][num_goals][metric]["sample"] = xvalues
					stats[topology][num_thresholds][num_goals][metric]["median"] = r.median(xvalues)[0]
					stats[topology][num_thresholds][num_goals][metric]["mean"] = r.mean(xvalues)[0]
					stats[topology][num_thresholds][num_goals][metric]["sd"] = r.sd(xvalues)[0]
					stats[topology][num_thresholds][num_goals][metric]["min"] = r.min(xvalues)[0]
					stats[topology][num_thresholds][num_goals][metric]["max"] = r.max(xvalues)[0]
					
					test_wilcoxon = wilcoxon_test_one_sample(xvalues)							
					error_max = test_wilcoxon[7][1]		
					median = test_wilcoxon[8][0]

					stats[topology][num_thresholds][num_goals][metric]["wilcoxon_test_median"] = median
					stats[topology][num_thresholds][num_goals][metric]["wilcoxon_test_error"] = float(error_max)-float(median)

					#print(topology+"-"+num_thresholds+"-"+num_goals+"-"+metric)

					title_graph = topology+"-"+num_thresholds+"-"+num_goals+"-"+metric
					xlabel = metrics_title[metric]
					# Histogram
					r.pdf("../figures/eda/hist-"+topology+"-"+num_thresholds+"-"+num_goals+"-"+metric+".pdf")
		 			r.hist(xvalues, main = title_graph, col="blue", xlab = xlabel, ylab = "Absolute Frequency")
		 			close_pdf()
		 			# Boxplots
		 			r.pdf("../figures/eda/box-"+topology+"-"+num_thresholds+"-"+num_goals+"-"+metric+".pdf")
		 			r.boxplot(xvalues, main = title_graph,col="lightblue", horizontal=True, las=1, xlab=xlabel)
		 			close_pdf()

		file.close()

######################################################################################

#for key in stats["high"].keys():
#	for key2 in stats["high"][key]:
#		print(key+"-"+key2)


#print(stats["high"]["1"].keys())

fig_num=0
colors = ["b","g","r","k"]
markers = ["o","^","s","+"]
topologies.sort()

figures_y = ["planning-time","searching-time","size"]
figures_x = ["range"]
figures_type = ["line"]

factors = ["threshold", "goal"]
legends_title = {"threshold":"Alarms","goal":"Goals"}

######################################################################################

# FIGURES

for figure_y in figures_y:
	fig_num+=1
	plt.figure(fig_num)
	error_config = {'ecolor': 'black'}
	ind = np.arange(len(list_num_thresholds))

	fig, ax = plt.subplots(sharex=False, sharey=False)
	charts = []

	ind_color = 0
	ind_marker = 0	
	
	legends = []
	for factor in factors:
		if ((figure_y == "planning-time") or (figure_y == "searching-time")):
			vectors_median = {"values":[],"errors":[]}
		else:
			vectors_files_size = []

		elements = list_num_thresholds

		for element in elements:
			if ((figure_y == "planning-time") or (figure_y == "searching-time")):
				if (figure_y == "planning-time"):
					metric = "planning"
				else:
					metric = "search"
				if (factor == "threshold"):
					median = stats["high"][element][fixed_level][metric]["wilcoxon_test_median"] 
					error = stats["high"][element][fixed_level][metric]["wilcoxon_test_error"]							
				else:
					median = stats["high"][fixed_level][element][metric]["wilcoxon_test_median"] 
					error = stats["high"][fixed_level][element][metric]["wilcoxon_test_error"]
				vectors_median["values"].append(median)
				vectors_median["errors"].append(error)
			else:
				if (factor == "threshold"):
					vectors_files_size.append(float(stats["high"][element][fixed_level]["file_size"])/1000000)
				else: 
					vectors_files_size.append(float(stats["high"][fixed_level][element]["file_size"])/1000000)

		if ((figure_y == "planning-time") or (figure_y == "searching-time")):
			lineChart = ax.plot(ind, vectors_median["values"], color=colors[ind_color], marker=markers[ind_marker], markersize=10)
			ax.errorbar(ind, vectors_median["values"], color=colors[ind_color], yerr=vectors_median["errors"])					
		else: 
			lineChart = ax.plot(ind, vectors_files_size, color=colors[ind_color], marker=markers[ind_marker], markersize=10)
		
		charts.append(lineChart)
			
		ind_color+=1
		ind_marker+=1
		legends.append(legends_title[factor])
		
	ax.set_xticks(ind)
	ax.set_xticklabels(list_num_thresholds,fontsize=16)
	ax.legend((charts[0][0], charts[1][0]), (legends[0], legends[1]),bbox_to_anchor=(0., 1.02, 1., .102), loc=2,ncol=2, mode="expand", borderaxespad=0.)

	ax.tick_params(labelsize=16)
	ax.grid(True)

	xlabel = "Number of Elements"

	if (figure_y == "planning-time"):
		ylabel = "Planning - Total Time (s)"
	elif (figure_y == "searching-time"):
		ylabel = "Planning - Search Time (s)"
	else: 
		ylabel = "Ontology File Size (MB)"
		
			
	fig.text(0.5, 0.005, xlabel, ha='center', fontsize=18)
	fig.text(0.005, 0.5, ylabel, va='center', rotation='vertical', fontsize=18)

	plt.savefig("../figures/charts/line-"+figure_y+".pdf",format='pdf')


figures_y = ["consistency"]
#legends = ["Threshold - Before Plan","Threshold - After Plan", "Goal - Before Plan", "Goal - After Plan"]
factors = ["threshold", "goal"]
metrics = ["consistency","plancon"]
legends_title = {"consistency":{"threshold":"Alarms (Pre)","goal":"Goals (Pre)"},"plancon":{"threshold":"Alarms (Pos)","goal":"Goals (Pos)"}}
# FIGURES

for figure_y in figures_y:
	fig_num+=1
	plt.figure(fig_num)
	ind = np.arange(len(list_num_thresholds))

	fig, ax = plt.subplots(sharex=False, sharey=False)
	charts = []

	ind_color = 0
	ind_marker = 0	
	
	legends = []
	for metric in metrics:
		for factor in factors:
			vectors_median = {"values":[],"errors":[]}
			elements = list_num_thresholds
			for element in elements:
				if (factor == "threshold"):
					median = stats["high"][element][fixed_level][metric]["wilcoxon_test_median"] 
					error = stats["high"][element][fixed_level][metric]["wilcoxon_test_error"]							
				else:
					median = stats["high"][fixed_level][element][metric]["wilcoxon_test_median"] 
					error = stats["high"][fixed_level][element][metric]["wilcoxon_test_error"]
				vectors_median["values"].append(median)
				vectors_median["errors"].append(error)
			lineChart = ax.plot(ind, vectors_median["values"], color=colors[ind_color], marker=markers[ind_marker], markersize=10)
			ax.errorbar(ind, vectors_median["values"], color=colors[ind_color], yerr=vectors_median["errors"])
			charts.append(lineChart)
			legends.append(legends_title[metric][factor])
			ind_color+=1
			ind_marker+=1

	ax.set_xticks(ind)
	ax.set_xticklabels(list_num_thresholds,fontsize=16)
	ax.legend((charts[0][0], charts[1][0], charts[2][0], charts[3][0]), (legends[0], legends[1], legends[2], legends[3]),bbox_to_anchor=(0., 1.02, 1., .102), loc=3,ncol=4, mode="expand", borderaxespad=0.)

	ax.tick_params(labelsize=16)
	ax.grid(True)

	xlabel = "Number of Elements"
	ylabel = "Consistency Check Time (s)"		
			
	fig.text(0.5, 0.005, xlabel, ha='center', fontsize=18)
	fig.text(0.005, 0.5, ylabel, va='center', rotation='vertical', fontsize=18)

	plt.savefig("../figures/charts/line-"+figure_y+".pdf",format='pdf')