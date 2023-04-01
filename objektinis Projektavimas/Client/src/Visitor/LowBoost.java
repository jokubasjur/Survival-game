package Visitor;

import Mediator.BoostsMediator;

public class LowBoost extends BoostElement{

	public LowBoost(BoostsMediator mediator) {
		setMediator(mediator);
		this.accessRate = 50;
	}
	
	@Override
	public void boost(ImprovementVisitor visitor) {
		visitor.activateLowBoost(this);
		sendNotification();
	}

	@Override
	public void sendNotification() {
		if (getMediator() != null) {
			getMediator().broadcast("low");
		}
	}

	@Override
	public void receiveNotification(String boostType) {
		switch (boostType) {
			case "low":
				accessRate = accessRate - 5;
				break;
			case "mid":
				accessRate = accessRate + 5;
				break;
			case "high":
				accessRate = accessRate + 5;
				break;
		}
		
		if (accessRate < 20) {
			accessRate = 20;
		}
		System.out.println("LOW BOOST CHANCE: " + accessRate);
	}
}
