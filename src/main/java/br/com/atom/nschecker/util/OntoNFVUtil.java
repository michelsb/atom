package br.com.atom.nschecker.util;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;

public class OntoNFVUtil {

	public static final @Nonnull IRI NFV_IRI = IRI.create("http://cin.ufpe.br/msb6/ontologies/2018/1/", "onto-nfv.owl");
	//public static final @Nonnull String BASEDIR = "resources/base-ontology/";
	public static final @Nonnull String DBDIR = "database/";
	public static final @Nonnull String ONTOFILE = DBDIR+"onto-nfv.owl";	
	
}
