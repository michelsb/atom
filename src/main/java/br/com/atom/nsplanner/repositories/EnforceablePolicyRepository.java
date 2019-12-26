package br.com.atom.nsplanner.repositories;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.ActionDto;
import br.com.atom.nsplanner.dtos.ConditionDto;
import br.com.atom.nsplanner.dtos.RuleDto;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;

public class EnforceablePolicyRepository {

	protected OntologyManager ontomanager;

	public EnforceablePolicyRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public RuleDto getPolicyRule(String indPolicyRuleName) {

		/*ReasonerFactory reasonerFactory = new ReasonerFactory();
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(), configuration);
		reasoner.precomputeInferences();*/
		
		RuleDto rule = new RuleDto();
		rule.setName(indPolicyRuleName);

		ArrayList<String> events = new ArrayList<String>();
		Set<OWLNamedIndividual> owlPolicyEvents = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyRuleName,
				NamedObjectProp.HASPOLICYEVENT);
		for (OWLNamedIndividual owlPolicyEvent : owlPolicyEvents) {
			String indPolicyEventName = owlPolicyEvent.getIRI().getShortForm();
			Set<OWLNamedIndividual> owlEvents = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyEventName,
					NamedObjectProp.HASEVENT);
			for (OWLNamedIndividual owlEvent : owlEvents) {
				String indEventName = owlEvent.getIRI().getShortForm();
				events.add(indEventName);
			}
		}
		rule.setEvents(events);

		ArrayList<ActionDto> actions = new ArrayList<ActionDto>();
		Set<OWLNamedIndividual> owlPolicyActions = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyRuleName,
				NamedObjectProp.HASPOLICYACTION);
		for (OWLNamedIndividual owlPolicyAction : owlPolicyActions) {
			String indPolicyActionName = owlPolicyAction.getIRI().getShortForm();
			Set<OWLNamedIndividual> owlActions = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyActionName,
					NamedObjectProp.HASACTION);
			for (OWLNamedIndividual owlAction : owlActions) {
				ActionDto action = new ActionDto();
				String indActionName = owlAction.getIRI().getShortForm();
				action.setName(indActionName);
				Set<OWLNamedIndividual> owlSubjects = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indActionName,
						NamedObjectProp.HASSUBJECT);
				for (OWLNamedIndividual owlSubject : owlSubjects) {
					String indSubjectName = owlSubject.getIRI().getShortForm();
					action.setSubject(indSubjectName);
					break;
				}
				Set<OWLNamedIndividual> owlTargets = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indActionName,
						NamedObjectProp.HASTARGET);
				for (OWLNamedIndividual owlTarget : owlTargets) {
					String indTargetName = owlTarget.getIRI().getShortForm();
					action.setTarget(indTargetName);
					break;
				}
				actions.add(action);
			}
		}
		rule.setActions(actions);

		ArrayList<ConditionDto> conditions = new ArrayList<ConditionDto>();
		Set<OWLNamedIndividual> owlPolicyConditions = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyRuleName,
				NamedObjectProp.HASPOLICYCONDITION);
		for (OWLNamedIndividual owlPolicyCondition : owlPolicyConditions) {
			String indPolicyConditionName = owlPolicyCondition.getIRI().getShortForm();
			ConditionDto condition = new ConditionDto();
			Set<OWLNamedIndividual> owlKeys = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyConditionName,
					NamedObjectProp.HASPARAMETERKEY);
			for (OWLNamedIndividual owlKey : owlKeys) {
				String indKeyName = owlKey.getIRI().getShortForm();
				condition.setKey(indKeyName);
				break;
			}
			Set<OWLLiteral> values = ontomanager.getDpValuesPerIndividualWithoutReasoner(indPolicyConditionName,
					NamedDataProp.HASPARAMETERVALUE);
			ArrayList<String> vals = new ArrayList<String>(); 
			for (OWLLiteral value : values) {
				vals.add(value.getLiteral());
			}
			condition.setValues(vals);
			conditions.add(condition);
		}
		rule.setConditions(conditions);

		return rule;
	}

	public ArrayList<RuleDto> getPolicyRules() {
		
		/*ReasonerFactory reasonerFactory = new ReasonerFactory();
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(), configuration);
		reasoner.precomputeInferences();*/
		
		ArrayList<RuleDto> rules = new ArrayList<RuleDto>();

		Set<OWLNamedIndividual> owlPolicyRules = this.ontomanager.getClassIndividualsWithoutReasoner(NamedClasses.POLICYRULE);

		for (OWLNamedIndividual owlPolicyRule : owlPolicyRules) {
			RuleDto rule = new RuleDto();
			String indPolicyRuleName = owlPolicyRule.getIRI().getShortForm();
			rule.setName(indPolicyRuleName);

			ArrayList<String> events = new ArrayList<String>();
			Set<OWLNamedIndividual> owlPolicyEvents = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyRuleName,
					NamedObjectProp.HASPOLICYEVENT);
			for (OWLNamedIndividual owlPolicyEvent : owlPolicyEvents) {
				String indPolicyEventName = owlPolicyEvent.getIRI().getShortForm();
				Set<OWLNamedIndividual> owlEvents = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyEventName,
						NamedObjectProp.HASEVENT);
				for (OWLNamedIndividual owlEvent : owlEvents) {
					String indEventName = owlEvent.getIRI().getShortForm();
					events.add(indEventName);
				}
			}
			rule.setEvents(events);

			ArrayList<ActionDto> actions = new ArrayList<ActionDto>();
			Set<OWLNamedIndividual> owlPolicyActions = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyRuleName,
					NamedObjectProp.HASPOLICYACTION);
			for (OWLNamedIndividual owlPolicyAction : owlPolicyActions) {
				String indPolicyActionName = owlPolicyAction.getIRI().getShortForm();
				Set<OWLNamedIndividual> owlActions = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyActionName,
						NamedObjectProp.HASACTION);
				for (OWLNamedIndividual owlAction : owlActions) {
					ActionDto action = new ActionDto();
					String indActionName = owlAction.getIRI().getShortForm();
					action.setName(indActionName);
					Set<OWLNamedIndividual> owlSubjects = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indActionName,
							NamedObjectProp.HASSUBJECT);
					for (OWLNamedIndividual owlSubject : owlSubjects) {
						String indSubjectName = owlSubject.getIRI().getShortForm();
						action.setSubject(indSubjectName);
						break;
					}
					Set<OWLNamedIndividual> owlTargets = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indActionName,
							NamedObjectProp.HASTARGET);
					for (OWLNamedIndividual owlTarget : owlTargets) {
						String indTargetName = owlTarget.getIRI().getShortForm();
						action.setTarget(indTargetName);
						break;
					}
					actions.add(action);
				}
			}
			rule.setActions(actions);

			ArrayList<ConditionDto> conditions = new ArrayList<ConditionDto>();
			Set<OWLNamedIndividual> owlPolicyConditions = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyRuleName,
					NamedObjectProp.HASPOLICYCONDITION);
			for (OWLNamedIndividual owlPolicyCondition : owlPolicyConditions) {
				String indPolicyConditionName = owlPolicyCondition.getIRI().getShortForm();
				ConditionDto condition = new ConditionDto();
				Set<OWLNamedIndividual> owlKeys = this.ontomanager.getOpValuesPerIndividualWithoutReasoner(indPolicyConditionName,
						NamedObjectProp.HASPARAMETERKEY);
				for (OWLNamedIndividual owlKey : owlKeys) {
					String indKeyName = owlKey.getIRI().getShortForm();
					condition.setKey(indKeyName);
					break;
				}
				Set<OWLLiteral> values = ontomanager.getDpValuesPerIndividualWithoutReasoner(indPolicyConditionName,
						NamedDataProp.HASPARAMETERVALUE);
				ArrayList<String> vals = new ArrayList<String>(); 
				for (OWLLiteral value : values) {
					vals.add(value.getLiteral());
				}
				condition.setValues(vals);
				conditions.add(condition);
			}
			rule.setConditions(conditions);
		}

		return rules;
	}
}
