package br.com.atom.nsplanner.evaluation.util;

import javax.annotation.Nonnull;


import org.semanticweb.owlapi.model.IRI;

import br.com.atom.nsplanner.util.NamedDataProp;

public class OntoPlannerUtilTest {

	public static final @Nonnull IRI NFV_IRI = IRI.create("http://cin.ufpe.br/msb6/ontologies/2019/8/", "onto-planner.owl");
	public static final @Nonnull String BASEDIR = "nsplanner-simulator-resources/";
	public static final @Nonnull String ONTODIR = BASEDIR+"base-ontology/";
	public static final @Nonnull String ONTOTEMPDIR = BASEDIR+"created-ontologies/";
	public static final @Nonnull String GMLTOPODIR = BASEDIR+"topology-files/";
	public static final @Nonnull String RESULTDIR = BASEDIR+"result-files/";
	public static final @Nonnull String LISPDIR = BASEDIR+"planner-files/";
	public static final @Nonnull String ONTOFILE = ONTODIR+"onto-planner.owl";
	public static final @Nonnull String CONFIGFILE = BASEDIR+"config-files/config.properties";
	
	public static final @Nonnull String PLAN_DOMAIN_NAME = "nfvmano-domain";
	public static final @Nonnull String PLAN_PROBLEM_NAME = "nfvmano-problem";
	public static final @Nonnull String PLAN_DOMAIN_FILE = LISPDIR+"planning-domain.lisp";
	public static final @Nonnull String PLAN_PROBLEM_FILE = LISPDIR+"planning-problem.lisp";
	
	public static final @Nonnull String SCALINGGROUP_TEMPLATE_NAME = "sg_template";
	public static final @Nonnull String SCALINGPOLICY_TEMPLATE_NAME = "sp_template";
	public static final @Nonnull String HEALINGPOLICY_TEMPLATE_NAME = "hp_template";
	
	public static final @Nonnull String HEALING_THRESHOLD_VALUES_DP[] = new String[] {NamedDataProp.HASLOWTHRESHOLDVALUE, NamedDataProp.HASMEDIUMTHRESHOLDVALUE, NamedDataProp.HASHIGHTHRESHOLDVALUE};
	
	public static final @Nonnull String SCALING_THRESHOLD_VALUES_DP[] = new String[] {NamedDataProp.HASLOWSCALEINTHRESHOLDVALUE, NamedDataProp.HASLOWSCALEOUTTHRESHOLDVALUE, 
			NamedDataProp.HASMEDIUMSCALEINTHRESHOLDVALUE, NamedDataProp.HASMEDIUMSCALEOUTTHRESHOLDVALUE, 
			NamedDataProp.HASHIGHSCALEINTHRESHOLDVALUE, NamedDataProp.HASHIGHSCALEOUTTHRESHOLDVALUE};
	
	
	
}
