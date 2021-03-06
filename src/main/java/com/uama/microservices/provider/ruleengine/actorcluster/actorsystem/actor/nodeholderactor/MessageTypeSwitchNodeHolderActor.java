package com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.actor.nodeholderactor;

import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.collect.BiMap;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.ActorSystemContext;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.actor.ActorTypeMapperEnum;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.actor.NodeHolderActor;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.actor.processor.nodeholderprocessor.DefaultNodeHolderProcessor;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.entity.NodeId;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.entity.NodeLifeCycleInfo;
import com.uama.microservices.provider.ruleengine.actorcluster.actorsystem.message.NodeMessageCarrier;

import java.util.List;
import java.util.Map;

/**
 * @program: uama-microservices-iot-ulink-rule-engine
 * @description:
 * @author: liwen
 * @create: 2019-05-09 22:13
 **/
public class MessageTypeSwitchNodeHolderActor extends NodeHolderActor<DefaultNodeHolderProcessor> {
    
    public MessageTypeSwitchNodeHolderActor(ActorSystemContext actorSystemContext, BiMap<NodeId, ActorRef> nodeIdMap, Map<NodeId, List<NodeMessageCarrier>> lockedFirstNodeIdVMap, Map<NodeId, NodeLifeCycleInfo> nodeLifeCycleMap) {
        super(actorSystemContext, nodeIdMap, lockedFirstNodeIdVMap, nodeLifeCycleMap);
    }
    
    @Override
    protected void initActorProcessor() {
        super.actorProcessor = new DefaultNodeHolderProcessor(this);
    }
    
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(NodeMessageCarrier.class, super.actorProcessor::dispatchMessage)
                .matchAny(super.actorProcessor::processUnknownMessage)
                .build();
    }
    
    @Override
    public ActorTypeMapperEnum getType() {
        return ActorTypeMapperEnum.MESSAGE_TYPE_SWITCH;
    }
}
