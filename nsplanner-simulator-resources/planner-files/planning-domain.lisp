(in-package :shop-user)
; This extremely simple example shows some of the most essential
;   features of SHOP2.

(defdomain nfvmano-domain (
  ;STATE AXIOMS
  (:- (different ?x ?y) ((not (same ?x ?y))))
  (:- (same ?x ?x) nil)

  (:op (!!create_low_scaling_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal low)
          (SCALINGGROUP ?sg)
      )   
    :add (
          (forall (?thr) 
            (
              (SCALINGTHRESHOLD ?thr) 
              (hasThreshold horizontal_scale ?thr) 
              (hasMetric ?thr ?metric) 
              (hasLowScaleInThresholdValue ?thr ?inLimit)
              (hasLowScaleOutThresholdValue ?thr ?outLimit)
            ) 
            (
              (SCALINGCRITERIA ?thr)  
              (hasScaleInThreshold ?thr ?inLimit)
              (hasScaleOutThreshold ?thr ?outLimit)
              (hasScaleInRelationalOperation ?thr LT)
              (hasScaleOutRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasScalingCriteria ?sg ?thr)
            )
          )               
      )
  )

  (:op (!!create_medium_scaling_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal medium)
          (SCALINGGROUP ?sg)
      )   
    :add (
          (forall (?thr) 
            (
              (SCALINGTHRESHOLD ?thr) 
              (hasThreshold horizontal_scale ?thr) 
              (hasMetric ?thr ?metric) 
              (hasMediumScaleInThresholdValue ?thr ?inLimit)
              (hasMediumScaleOutThresholdValue ?thr ?outLimit)
            ) 
            (
              (SCALINGCRITERIA ?thr)  
              (hasScaleInThreshold ?thr ?inLimit)
              (hasScaleOutThreshold ?thr ?outLimit)
              (hasScaleInRelationalOperation ?thr LT)
              (hasScaleOutRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasScalingCriteria ?sg ?thr)
            )
          )               
      )
  )

  (:op (!!create_high_scaling_criteria) 
  	:precond (and 
  				(GOAL ?goal) 
  				(hasLevel ?goal high)
  				(SCALINGGROUP ?sg)
  		)   
  	:add (
  		    (forall (?thr) 
            (
              (SCALINGTHRESHOLD ?thr) 
              (hasThreshold horizontal_scale ?thr) 
              (hasMetric ?thr ?metric) 
              (hasHighScaleInThresholdValue ?thr ?inLimit)
              (hasHighScaleOutThresholdValue ?thr ?outLimit)
            ) 
            (
              (SCALINGCRITERIA ?thr)  
              (hasScaleInThreshold ?thr ?inLimit)
              (hasScaleOutThreshold ?thr ?outLimit)
              (hasScaleInRelationalOperation ?thr LT)
              (hasScaleOutRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasScalingCriteria ?sg ?thr)
            )
          )  			  			
  		)
  )  

  (:op (!!create_low_restarting_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal low)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold restart ?thr) 
              (hasMetric ?thr ?metric) 
              (hasLowThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr restart)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_medium_restarting_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal medium)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold restart ?thr) 
              (hasMetric ?thr ?metric) 
              (hasMediumThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr restart)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_high_restarting_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal high)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold restart ?thr) 
              (hasMetric ?thr ?metric) 
              (hasHighThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr restart)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_low_turningoff_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal low)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold turnoff ?thr) 
              (hasMetric ?thr ?metric) 
              (hasLowThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr turnoff)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_medium_turningoff_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal medium)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold turnoff ?thr) 
              (hasMetric ?thr ?metric) 
              (hasMediumThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr turnoff)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_high_turningoff_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal high)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold turnoff ?thr) 
              (hasMetric ?thr ?metric) 
              (hasHighThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr turnoff)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_low_redeploying_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal low)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold redeploy ?thr) 
              (hasMetric ?thr ?metric) 
              (hasLowThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr redeploy)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_medium_redeploying_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal medium)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold redeploy ?thr) 
              (hasMetric ?thr ?metric) 
              (hasMediumThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr redeploy)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_high_redeploying_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal high)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold redeploy ?thr) 
              (hasMetric ?thr ?metric) 
              (hasHighThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr redeploy)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_low_migrating_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal low)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold migrate ?thr) 
              (hasMetric ?thr ?metric) 
              (hasLowThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr migrate)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_medium_migrating_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal medium)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold migrate ?thr) 
              (hasMetric ?thr ?metric) 
              (hasMediumThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr migrate)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_high_migrating_criteria) 
    :precond (and 
          (GOAL ?goal) 
          (hasLevel ?goal high)
          (HEALINGGROUP ?hg)
      )   
    :add (
          (forall (?thr) 
            (
              (HEALINGTHRESHOLD ?thr) 
              (hasThreshold migrate ?thr) 
              (hasMetric ?thr ?metric) 
              (hasHighThresholdValue ?thr ?limit)
            ) 
            (
              (HEALINGCRITERIA ?thr)
              (hasActionType ?thr migrate)  
              (hasHealThreshold ?thr ?limit)
              (hasHealRelationalOperation ?thr GT)
              (hasNsMonitoringParamRef ?thr ?metric)
              (hasHealingCriteria ?hg ?thr)
            )
          )               
      )
  )

  (:op (!!create_scaling_group) 
    :precond (and     
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((VNF ?vnf)))
      (assign ?sgName (call gensym))      
      )   
    :add (
      (SCALINGGROUP ?sgName)      
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasMemberVNFIndexRef ?sgName ?index)))     
      ) 
  )

  (:op (!create_scaling_policies) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal)
      (forall (?sg) ((SCALINGGROUP ?sg)) ((SCALINGGROUP ?sg)))
      (assign ?polRuleName 'pol_create_scaling_policies)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))    
      )
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_scaling_policies)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
    )   
  )

  (:op (!!create_healing_group) 
    :precond (and     
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((VNF ?vnf)))
      (assign ?hgName (call gensym))      
      )   
    :add (
      (HEALINGGROUP ?hgName)      
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasMemberVNFIndexRef ?hgName ?index)))     
      ) 
  )

  (:op (!create_healing_policies) 
    :precond (and 
      (NS ?ns)
      (GOAL ?goal)    
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))    
      (assign ?polRuleName 'pol_create_healing_policies)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))    
      )
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_healing_policies)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
    )     
  )

  (:op (!configure_failures_prediction_mechanisms) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (assign ?polRuleName 'pol_configure_failures_prediction_mechanisms)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))     
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName configure_failures_prediction_mechanisms)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
    )   
  )

  (:op (!configure_dynamic_traffic_fluctuations_mechanisms) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (assign ?polRuleName 'pol_configure_dynamic_traffic_fluctuations_mechanisms)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))     
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName configure_dynamic_traffic_fluctuations_mechanisms)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
    )     
  )

  (:op (!create_phy_nic_bonding) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal low)
      (assign ?polRuleName 'pol_create_phy_nic_bonding)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_phy_nic_bonding)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!create_nic_bonding_of_vnics) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal medium)
      (assign ?polRuleName 'pol_create_nic_bonding_of_vnics)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_nic_bonding_of_vnics)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )
  
  (:op (!create_vnf_internal_redundancy) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal high)
      (assign ?polRuleName 'pol_create_vnf_internal_redundancy)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_vnf_internal_redundancy)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!subscribe_link_down_event_nic_redundancy) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (assign ?polRuleName 'pol_subscribe_link_down_event_nic_redundancy)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa006_subscribe_link_down_event)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!configure_recovery_rule_nic_redundancy) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (assign ?polRuleName 'pol_configure_recovery_rule_nic_redundancy)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))
      (assign ?polConditionName3 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (POLICYCONDITION ?polConditionName3)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName alarm_notified)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa006_migrate_vcomp)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
      (hasPolicyCondition ?polRuleName ?polConditionName3)
      (hasParameterKey ?polConditionName3 alarm_type)
      (hasParameterValue ?polConditionName3 link_down)
    )
  )

  (:op (!create_load_sharing_between_links) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal low)
      (assign ?polRuleName 'pol_create_load_sharing_between_links)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))     
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_load_sharing_between_links)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
    ) 
  )

  (:op (!create_link_active_active_redundancy) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal high)
      (assign ?polRuleName 'pol_create_link_active_active_redundancy)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_link_active_active_redundancy)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!create_link_active_standby_redundancy) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal medium)
      (assign ?polRuleName 'pol_create_link_active_standby_redundancy)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName create_link_active_standby_redundancy)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!subscribe_link_out_of_service_event) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal)
      (or (hasLevel ?goal high) (hasLevel ?goal medium))  
      (assign ?polRuleName 'pol_subscribe_link_out_of_service_event)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa005_subscribe_link_out_of_service_event)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!configure_recovery_rule_link_redundancy) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium)) 
      (assign ?polRuleName 'pol_configure_recovery_rule_link_redundancy)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))
      (assign ?polConditionName3 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (POLICYCONDITION ?polConditionName3)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName alarm_notified)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa005_migrate_vcomp)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
      (hasPolicyCondition ?polRuleName ?polConditionName3)
      (hasParameterKey ?polConditionName3 alarm_type)
      (hasParameterValue ?polConditionName3 link_out_of_service)
    )
  )

  (:op (!create_vnf_backup) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium)) 
      (assign ?polRuleName 'pol_create_vnf_backup)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa005_allocate_vcomp)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!set_vnf_backup_configuration) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium)) 
      (assign ?polRuleName 'pol_set_vnf_backup_configuration)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName success_resource_response)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa008_set_configuration)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!transfer_vnf_state_to_logical_unit) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal medium) 
      (assign ?polRuleName 'pol_transfer_vnf_state_to_logical_unit)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa008_set_configuration)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!transfer_vnf_state_to_vnf_backup) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (hasLevel ?goal high) 
      (assign ?polRuleName 'pol_transfer_vnf_state_to_vnf_backup)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName success_resource_response)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa008_set_configuration)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!subscribe_vnf_out_of_service_event) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium)) 
      (assign ?polRuleName 'pol_subscribe_vnf_out_of_service_event)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa008_subscribe_vnf_out_of_service_event)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
    )
  )

  (:op (!move_connections_to_vnf_backup) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium))
      (assign ?polRuleName 'pol_move_connections_to_vnf_backup)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))
      (assign ?polConditionName3 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (POLICYCONDITION ?polConditionName3)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName alarm_notified)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName move_conn_to_vnf_backup)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
      (hasPolicyCondition ?polRuleName ?polConditionName3)
      (hasParameterKey ?polConditionName3 alarm_type)
      (hasParameterValue ?polConditionName3 vnf_out_of_service)
    )
  )

  (:op (!retrieve_state_from_logical_unit) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal)
      (hasLevel ?goal medium) 
      (assign ?polRuleName 'pol_retrieve_state_from_logical_unit)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))
      (assign ?polConditionName3 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (POLICYCONDITION ?polConditionName3)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName alarm_notified)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName retrieve_state_from_logical_unit)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
      (hasPolicyCondition ?polRuleName ?polConditionName3)
      (hasParameterKey ?polConditionName3 alarm_type)
      (hasParameterValue ?polConditionName3 vnf_out_of_service)
    )
  )

  (:op (!terminate_vnf) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium))
      (assign ?polRuleName 'pol_terminate_vnf)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))
      (assign ?polConditionName2 (call gensym))
      (assign ?polConditionName3 (call gensym))      
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (POLICYCONDITION ?polConditionName2)
      (POLICYCONDITION ?polConditionName3)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName alarm_notified)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName ifa007_terminate_vnf)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
      (hasPolicyCondition ?polRuleName ?polConditionName2)
      (hasParameterKey ?polConditionName2 vnf_member_index)
      (forall (?vnf) ((VNF ?vnf) (hasMemberVNFIndex ?vnf ?index)) ((hasParameterValue ?polConditionName2 ?index)))
      (hasPolicyCondition ?polRuleName ?polConditionName3)
      (hasParameterKey ?polConditionName3 alarm_type)
      (hasParameterValue ?polConditionName3 vnf_out_of_service)
    )
  )

  (:op (!configure_multipath_routing) 
    :precond (and     
      (NS ?ns)
      (GOAL ?goal) 
      (assign ?polRuleName 'pol_configure_multipath_routing)
      (assign ?polEventName (call gensym))
      (assign ?polActionName (call gensym))
      (assign ?polConditionName1 (call gensym))     
      )  
    :add (
      (POLICYRULE ?polRuleName)
      (POLICYEVENT ?polEventName)
      (POLICYACTION ?polActionName)
      (POLICYCONDITION ?polConditionName1)
      (hasPolicyEvent ?polRuleName ?polEventName)
      (hasEvent ?polEventName vnfinst_instantiated)
      (hasPolicyAction ?polRuleName ?polActionName)
      (hasAction ?polActionName configure_multipath_routing)
      (hasPolicyCondition ?polRuleName ?polConditionName1)
      (hasParameterKey ?polConditionName1 ns_id)
      (hasParameterValue ?polConditionName1 ?ns)
    )      
  )

  ;METHODS
  (:method (print-current-state)
	((eval (print-current-state)))
	())

  ;;;;;; Sixth Level ;;;;;;;
  ;; Resiliency Goal

  (:method (link_active_standby_redundancy_set)
    ;precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task !create_link_active_standby_redundancy) (:task !subscribe_link_out_of_service_event) (:task !configure_recovery_rule_link_redundancy)  
    )     
  )

  (:method (link_active_active_redundancy_set)
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task !create_link_active_active_redundancy) (:task !subscribe_link_out_of_service_event) (:task !configure_recovery_rule_link_redundancy)  
    )     
  )

  ;;;;;; Fifth Level ;;;;;;;
  ;; Resiliency Goal

  (:method (dedicated_backup_links_set)
    ;precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task link_active_standby_redundancy_set)  
    )     
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task link_active_active_redundancy_set)  
    )
  ) 

  (:method (horizontal_scale_policy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered (:task !!create_scaling_group) (:task !!create_high_scaling_criteria) (:task !create_scaling_policies))

    ; precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered (:task !!create_scaling_group) (:task !!create_medium_scaling_criteria) (:task !create_scaling_policies))

    ; precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered (:task !!create_scaling_group) (:task !!create_low_scaling_criteria) (:task !create_scaling_policies))
  )

  (:method (phy_nic_bonding_set)
    ;precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered 
      (:task !create_phy_nic_bonding) (:task !subscribe_link_down_event_nic_redundancy) (:task !configure_recovery_rule_nic_redundancy)  
    )     
  )

  (:method (nic_bonding_of_vnics_set)
    ;precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task !create_nic_bonding_of_vnics) (:task !subscribe_link_down_event_nic_redundancy) (:task !configure_recovery_rule_nic_redundancy)  
    )     
  )

  (:method (vnf_internal_redundancy_set)
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task !create_vnf_internal_redundancy) (:task !subscribe_link_down_event_nic_redundancy) (:task !configure_recovery_rule_nic_redundancy)  
    )     
  )

  (:method (vnf_active_standby_redundancy_created)
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task !create_vnf_backup) (:task !set_vnf_backup_configuration) (:task !transfer_vnf_state_to_logical_unit)  
    )     
  )

  (:method (vnf_active_active_redundancy_created)
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task !create_vnf_backup) (:task !set_vnf_backup_configuration) (:task !transfer_vnf_state_to_vnf_backup)  
    )     
  )

  (:method (vnf_active_standby_rules_set)
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task !retrieve_state_from_logical_unit) (:task !move_connections_to_vnf_backup) (:task !terminate_vnf)  
    )     
  )

  (:method (vnf_active_active_rules_set)
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task !move_connections_to_vnf_backup) (:task !terminate_vnf)  
    )     
  )
  ;;;;;; Fourth Level ;;;;;;;
  ;; Resiliency Goal

  (:method (nic_redundancy_configured)    
    ;precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task vnf_internal_redundancy_set)  
    )
    ;precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task nic_bonding_of_vnics_set)  
    )
    ;precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered 
      (:task phy_nic_bonding_set)  
    )    
  ) 

  (:method (link_redundancy_configured)
    ;precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered 
      (:task !create_load_sharing_between_links)  
    ) 
    ;precond
    HIGHMEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task dedicated_backup_links_set)  
    )    
     
  ) 

  (:method (vnf_active_active_redundancy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered 
      (:task vnf_active_active_redundancy_created)  (:task !subscribe_vnf_out_of_service_event)  (:task vnf_active_active_rules_set)   
    )    
  )

  (:method (vnf_active_standby_redundancy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered 
      (:task vnf_active_standby_redundancy_created)  (:task !subscribe_vnf_out_of_service_event)  (:task vnf_active_standby_rules_set)   
    )    
  )

  (:method (auto_scaling_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:unordered 
      (:task horizontal_scale_policy_set)  
    )
  )

  ;;;;;; Third Level ;;;;;;;
  ;; Resiliency Goal

  (:method (nfvi_redundancy_configured)
    ; precond
    ;HIGHMEDIUM 
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      ;(or (hasLevel ?goal high) (hasLevel ?goal medium))
    )
    ; task list
    (:unordered 
      (:task nic_redundancy_configured)
      (:task link_redundancy_configured)  
    )
    ; LOW (
    ;   (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    ;   (GOAL ?goal) 
    ;   (hasLevel ?goal low)
    ; )
    ; ; task list
    ; (:unordered 
    ;   (:task nic_redundancy_configured)
    ; )
  ) 

  (:method (vnf_redundancy_configured)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered
      ;(:task !!set_property vnf_redundancy_enabled) 
      (:task vnf_active_active_redundancy_set)  
    )
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered
      ;(:task !!set_property vnf_redundancy_enabled) 
      (:task vnf_active_standby_redundancy_set) 
    )
  )

  (:method (qos_commitment_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:unordered 
      (:task auto_scaling_configured)  
    )
  )

  (:method (congestion_state_solved)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:unordered 
      (:task !configure_multipath_routing)  
    )
  )

  (:method (migrate_policy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered (:task !!create_high_migrating_criteria))
    ; precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered (:task !!create_medium_migrating_criteria))
    ; precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered (:task !!create_low_migrating_criteria))
  )

  (:method (redeploy_policy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered (:task !!create_high_redeploying_criteria))
    ; precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered (:task !!create_medium_redeploying_criteria))
    ; precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered (:task !!create_low_redeploying_criteria))
  )

  (:method (restart_policy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered (:task !!create_high_restarting_criteria))
    ; precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered (:task !!create_medium_restarting_criteria))
    ; precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered (:task !!create_low_restarting_criteria))
  )

  (:method (turnoff_policy_set)
    ; precond
    HIGH (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal high)
    )
    ; task list
    (:ordered (:task !!create_high_turningoff_criteria))
    ; precond
    MEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal medium)
    )
    ; task list
    (:ordered (:task !!create_medium_turningoff_criteria))
    ; precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:ordered (:task !!create_low_turningoff_criteria))
  )

  ;;;;;; Second Level ;;;;;;;
  ;; Resiliency Goal

  (:method (nfvi_protection_schemes_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task nfvi_redundancy_configured) 
    )
  )

  (:method (vnf_protection_schemes_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task vnf_redundancy_configured)      
    )
  )

  (:method (qos_degradation_prevented)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:unordered 
      (:task qos_commitment_configured) 
      (:task congestion_state_solved) 
    )
  )

  (:method (nfvi_fault_tolerance_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg))) 
    )
    ; task list
    (:unordered      
      (:task migrate_policy_set)  
    )
  )

  (:method (vnf_fault_tolerance_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (forall (?hg) ((HEALINGGROUP ?hg)) ((HEALINGGROUP ?hg))) 
    )
    ; task list
    (:unordered      
      (:task redeploy_policy_set) 
      (:task restart_policy_set)
      (:task turnoff_policy_set) 
    )
  )

  ;;;;;; First Level ;;;;;;;
  ;; Resiliency Goal

  (:method (failures_prevented)
    ; precond
    HIGHMEDIUM (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (or (hasLevel ?goal high) (hasLevel ?goal medium))
    )
    ; task list
    (:unordered 
      (:task nfvi_protection_schemes_configured) 
      (:task vnf_protection_schemes_configured) 
      (:task qos_degradation_prevented)
    )
    ; precond
    LOW (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
      (GOAL ?goal) 
      (hasLevel ?goal low)
    )
    ; task list
    (:unordered 
      (:task nfvi_protection_schemes_configured) 
      (:task qos_degradation_prevented)
    )
  )

  (:method (failures_controlled)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task !!create_healing_group)
      (:task nfvi_fault_tolerance_configured) 
      (:task vnf_fault_tolerance_configured)
      (:task !create_healing_policies) 
    )
  )

  (:method (failures_prediction_configured)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task !configure_failures_prediction_mechanisms) 
    )
  )

  (:method (dynamic_traffic_fluctuations_managed)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task !configure_dynamic_traffic_fluctuations_mechanisms) 
    )
  )

  ;;;;;; High-level Goals ;;;;;;;

  ;; Resiliency Goal
  (:method (resilience_goal_achieved)
    ; precond
    (
      (forall (?ns) ((NS ?ns)) ((NS ?ns)))
    )
    ; task list
    (:ordered 
      (:task failures_prevented) 
      (:task failures_controlled) 
      (:task failures_prediction_configured)
      (:task dynamic_traffic_fluctuations_managed)
    )
  )


))

