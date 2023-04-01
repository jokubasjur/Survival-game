package Mediator;

import java.util.ArrayList;

import Visitor.*;

public class BoostsMediator {
	private ArrayList<BoostElement> boosts = new ArrayList<>();
	
	public void addBoost(BoostElement boost) {
		boosts.add(boost);
	}
	
	public void broadcast(String boostType) {
		for (BoostElement boost : boosts) {
			boost.receiveNotification(boostType);
		}
	}
}
