package br.com.atom.nschecker.evaluation.util;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;

public class OntoNFVUtilTest {

	public static final @Nonnull IRI NFV_IRI = IRI.create("http://cin.ufpe.br/msb6/ontologies/2018/1/", "onto-nfv.owl");
	public static final @Nonnull String BASEDIR = "nschecker-simulator-resources/";
	public static final @Nonnull String ONTODIR = BASEDIR+"base-ontology/";
	public static final @Nonnull String ONTOTEMPDIR = BASEDIR+"created-ontologies/";
	public static final @Nonnull String GMLTOPODIR = BASEDIR+"topology-files/";
	public static final @Nonnull String RESULTDIR = BASEDIR+"result-files/";
	public static final @Nonnull String ONTOFILE = ONTODIR+"onto-nfv.owl";
	public static final @Nonnull String CONFIGFILE = BASEDIR+"config-files/config.properties";
	
}
