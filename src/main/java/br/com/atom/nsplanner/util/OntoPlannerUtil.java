package br.com.atom.nsplanner.util;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;

public class OntoPlannerUtil {

	public static final @Nonnull IRI NFV_IRI = IRI.create("http://cin.ufpe.br/msb6/ontologies/2019/8/", "onto-planner.owl");
	public static final @Nonnull String DBDIR = "database/";
	public static final @Nonnull String LISPDIR = "planner-files/";
	public static final @Nonnull String ONTOFILE = DBDIR+"onto-planner.owl";
	
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
