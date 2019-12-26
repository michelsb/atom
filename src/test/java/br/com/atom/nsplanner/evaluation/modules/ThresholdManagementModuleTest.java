package br.com.atom.nsplanner.evaluation.modules;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.classes.Threshold;
import br.com.atom.nsplanner.dtos.HealingThresholdDto;
import br.com.atom.nsplanner.dtos.HorizontalScalingThresholdDto;
import br.com.atom.nsplanner.dtos.LowLevelPolicyDto;
import br.com.atom.nsplanner.evaluation.repositories.ThresholdRepositoryTest;

public class ThresholdManagementModuleTest {

	protected OntologyManager ontomanager;
	protected ThresholdRepositoryTest thrDB;

	public ThresholdManagementModuleTest(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.thrDB = new ThresholdRepositoryTest(ontomanager);
	}

	public void createPolicy(LowLevelPolicyDto policy) {

		String prefix = policy.getName();
		if (policy.getHealPols() != null) {
			for (HealingThresholdDto healPol : policy.getHealPols()) {
				String thrName = prefix + " " + healPol.getName();
				Threshold thr = new Threshold();
				thr.setName(thrName);
				String thrIndName = this.thrDB.createHealingThresholdIndividual(thr);
				this.thrDB.configureHealingThreshold(thrIndName, healPol.getAction(), healPol.getMetric(),
						healPol.getLowThresholdValue(), healPol.getMediumThresholdValue(),
						healPol.getHighThresholdValue());
			}
		}

		if (policy.getHorScalePols() != null) {
			for (HorizontalScalingThresholdDto scalePol : policy.getHorScalePols()) {
				String thrName = prefix + " " + scalePol.getName();
				Threshold thr = new Threshold();
				thr.setName(thrName);
				String thrIndName = this.thrDB.createScalingThresholdIndividual(thr);
				this.thrDB.configureScalingThreshold(thrIndName, scalePol.getMetric(),
						scalePol.getLowScaleInThresholdValue(), scalePol.getLowScaleOutThresholdValue(),
						scalePol.getMediumScaleInThresholdValue(), scalePol.getMediumScaleOutThresholdValue(),
						scalePol.getHighScaleInThresholdValue(), scalePol.getHighScaleOutThresholdValue());
			}
		}

	}

}
