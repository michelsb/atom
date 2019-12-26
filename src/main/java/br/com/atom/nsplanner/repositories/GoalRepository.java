package br.com.atom.nsplanner.repositories;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nsplanner.classes.Goal;
import br.com.atom.nsplanner.responses.PlanResponse;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

public class GoalRepository {

	protected OntologyManager ontomanager;
	protected String initStateClasses[] = new String[] { NamedClasses.HEALINGACTION, NamedClasses.SCALINGACTION,
			NamedClasses.SCALINGTHRESHOLD, NamedClasses.HEALINGTHRESHOLD, NamedClasses.METRIC };
	protected String initStateOP[] = new String[] { NamedObjectProp.HASNS, NamedObjectProp.HASVNF,
			NamedObjectProp.HASLEVEL, NamedObjectProp.HASTHRESHOLD, NamedObjectProp.HASMETRIC };
	protected String initStateDP[] = new String[] { NamedDataProp.HASLOWSCALEINTHRESHOLDVALUE,
			NamedDataProp.HASLOWSCALEOUTTHRESHOLDVALUE, NamedDataProp.HASMEDIUMSCALEINTHRESHOLDVALUE,
			NamedDataProp.HASMEDIUMSCALEOUTTHRESHOLDVALUE, NamedDataProp.HASHIGHSCALEINTHRESHOLDVALUE,
			NamedDataProp.HASHIGHSCALEOUTTHRESHOLDVALUE, NamedDataProp.HASLOWTHRESHOLDVALUE,
			NamedDataProp.HASMEDIUMTHRESHOLDVALUE, NamedDataProp.HASHIGHTHRESHOLDVALUE,
			NamedDataProp.HASMEMBERVNFINDEX };

	public GoalRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public String createGoalIndividual(Goal goal) {
		String goalIndName = IndividualUtil.processNameForIndividual(goal.getName());
		OWLNamedIndividual ind = ontomanager.createIndividual(goalIndName, NamedClasses.GOAL);
		//ontomanager.makeDifferentFromOtherIndividuals(ind, NamedClasses.GOAL, ontomanager.createOWLReasoner());
		ontomanager.makeDifferentFromOtherIndividualsWithoutReasoner(ind, NamedClasses.GOAL);
		return goalIndName;
	}

	public String removeGoalIndividual(Goal goal) {
		String goalIndName = IndividualUtil.processNameForIndividual(goal.getName());
		ontomanager.removeIndividual(goalIndName);
		return goalIndName;
	}

	public void configureGoal(String goalIndName, String nsIndName, ArrayList<String> vnfIndNames,
			ArrayList<String> attributes, String level) {
		ontomanager.createObjectPropertyAssertionAxiom(goalIndName, level, NamedObjectProp.HASLEVEL);
		ontomanager.createObjectPropertyAssertionAxiom(goalIndName, nsIndName, NamedObjectProp.HASNS);
		for (String vnfIndName : vnfIndNames) {
			ontomanager.createObjectPropertyAssertionAxiom(goalIndName, vnfIndName, NamedObjectProp.HASVNF);
		}
		for (String att : attributes) {
			ontomanager.createObjectPropertyAssertionAxiom(goalIndName, att, NamedObjectProp.HASATTRIBUTE);
		}
	}

	public ArrayList<String> getThesholdInitialState(String actionClass, String thrClass, String[] thrValueDPs) {
		ArrayList<String> facts = new ArrayList<String>();

		Set<OWLNamedIndividual> owlActions = this.ontomanager.getClassIndividualsWithoutReasoner(actionClass);
		for (OWLNamedIndividual owlAction : owlActions) {
			String indActionName = owlAction.getIRI().getShortForm();
			String factAction = actionClass.toUpperCase() + " " + indActionName;
			facts.add(factAction);
			Set<OWLNamedIndividual> owlThrs = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indActionName,
					NamedObjectProp.HASTHRESHOLD);
			for (OWLNamedIndividual owlThr : owlThrs) {
				String indThrName = owlThr.getIRI().getShortForm();
				String factThr = thrClass.toUpperCase() + " " + indThrName;
				String factActionThr = NamedObjectProp.HASTHRESHOLD + " " + indActionName + " " + indThrName;
				facts.add(factThr);
				facts.add(factActionThr);
				Set<OWLNamedIndividual> owlMetrics = this.ontomanager
						.getOpValuesPerIndividualWithoutReasoner(indThrName, NamedObjectProp.HASMETRIC);
				for (OWLNamedIndividual owlMetric : owlMetrics) {
					String indMetricName = owlMetric.getIRI().getShortForm();
					String factMetric = NamedClasses.METRIC.toUpperCase() + " " + indMetricName;
					String factThrMetric = NamedObjectProp.HASMETRIC + " " + indThrName + " " + indMetricName;
					facts.add(factMetric);
					facts.add(factThrMetric);
				}
				for (String thrValueDP : thrValueDPs) {
					Set<OWLLiteral> owlValues = this.ontomanager.getDpValuesPerIndividualWithoutReasoner(indThrName,
							thrValueDP);
					for (OWLLiteral owlValue : owlValues) {
						String factThrValue = thrValueDP + " " + indThrName + " " + owlValue.getLiteral();
						facts.add(factThrValue);
					}
				}
			}
		}

		return facts;
	}

	public ArrayList<String> getGoalPlannerInitialState(Goal goal, PlanResponse response) {

		ArrayList<String> facts = new ArrayList<String>();

		// GETTING HEALING RULES
		facts.addAll(this.getThesholdInitialState(NamedClasses.HEALINGACTION, NamedClasses.HEALINGTHRESHOLD,
				OntoPlannerUtil.HEALING_THRESHOLD_VALUES_DP));
		// GETTING SCALING RULES
		facts.addAll(this.getThesholdInitialState(NamedClasses.SCALINGACTION, NamedClasses.SCALINGTHRESHOLD,
				OntoPlannerUtil.SCALING_THRESHOLD_VALUES_DP));
		// GETTING GOAL FACTS
		String goalIndName = IndividualUtil.processNameForIndividual(goal.getName());
		String factGoal = NamedClasses.GOAL.toUpperCase() + " " + goalIndName;
		facts.add(factGoal);
		Set<OWLNamedIndividual> owlNSs = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(goalIndName,
				NamedObjectProp.HASNS);
		for (OWLNamedIndividual owlNS : owlNSs) {
			String indNSName = owlNS.getIRI().getShortForm();
			String factNS = NamedClasses.NS.toUpperCase() + " " + indNSName;
			String factGoalNS = NamedObjectProp.HASNS + " " + goalIndName + " " + indNSName;
			facts.add(factNS);
			facts.add(factGoalNS);
			Set<OWLLiteral> owlValues = this.ontomanager.getDpValuesPerIndividualWithoutReasoner(indNSName,
					NamedDataProp.ID);
			for (OWLLiteral owlValue : owlValues) {
				response.getJsondata().getData().setNsrId(owlValue.getLiteral());
			}
		}
		Set<OWLNamedIndividual> owlVNFs = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(goalIndName,
				NamedObjectProp.HASVNF);
		for (OWLNamedIndividual owlVNF : owlVNFs) {
			String indVNFName = owlVNF.getIRI().getShortForm();
			String factVNF = NamedClasses.VNF.toUpperCase() + " " + indVNFName;
			String factGoalVNF = NamedObjectProp.HASVNF + " " + goalIndName + " " + indVNFName;
			facts.add(factVNF);
			facts.add(factGoalVNF);
			Set<OWLLiteral> owlValues = this.ontomanager.getDpValuesPerIndividualWithoutReasoner(indVNFName,
					NamedDataProp.HASMEMBERVNFINDEX);
			for (OWLLiteral owlValue : owlValues) {
				String factValue = NamedDataProp.HASMEMBERVNFINDEX + " " + indVNFName + " " + owlValue.getLiteral();
				facts.add(factValue);
				response.getJsondata().getData().getVnfMemberIndexes().add(owlValue.getLiteral());
			}

		}
		Set<OWLNamedIndividual> owlLevels = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(goalIndName,
				NamedObjectProp.HASLEVEL);
		for (OWLNamedIndividual owlLevel : owlLevels) {
			String indLevelName = owlLevel.getIRI().getShortForm();
			String factLevel = NamedClasses.LEVEL.toUpperCase() + " " + indLevelName;
			String factGoalLevel = NamedObjectProp.HASLEVEL + " " + goalIndName + " " + indLevelName;
			facts.add(factLevel);
			facts.add(factGoalLevel);
		}

		return facts;
	}

	public ArrayList<String> getGoalPlannerAttributes(Goal goal) {
		ArrayList<String> attributes = new ArrayList<String>();

		String goalIndName = IndividualUtil.processNameForIndividual(goal.getName());
		Set<OWLNamedIndividual> owlAtts = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(goalIndName,
				NamedObjectProp.HASATTRIBUTE);
		for (OWLNamedIndividual owlAtt : owlAtts) {
			String indAttName = owlAtt.getIRI().getShortForm();
			attributes.add(indAttName);
		}

		return attributes;
	}

	public ArrayList<String> getGoalIndividuals() {
		ArrayList<String> individuals = new ArrayList<String>();
		Set<OWLNamedIndividual> owlIndividuals = this.ontomanager.getClassIndividualsWithoutReasoner(NamedClasses.GOAL);
		for (OWLNamedIndividual owlInd : owlIndividuals) {
			individuals.add(owlInd.getIRI().getShortForm());
		}
		return individuals;
	}

}
