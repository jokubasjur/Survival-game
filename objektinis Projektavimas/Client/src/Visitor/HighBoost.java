package Visitor;

import Mediator.BoostsMediator;

public class HighBoost extends BoostElement{
	
	public HighBoost(BoostsMediator mediator) {
		setMediator(mediator);
		this.accessRate = 20;
	}
	
	@Override
	public void boost(ImprovementVisitor visitor) {
		visitor.activateHighBoost(this);
		sendNotification();
	}

	@Override
	public void sendNotification() {
		if (getMediator() != null) {
			getMediator().broadcast("high");
		}
	}

	@Override
	public void receiveNotification(String boostType) {
		switch (boostType) {
			case "low":
				accessRate = accessRate + 10;
				break;
			case "mid":
				accessRate = accessRate + 10;
				break;
			case "high":
				accessRate = accessRate - 5;
				break;
		}
		
		if (accessRate < 10) {
			accessRate = 10;
		}
		System.out.println("HIGH BOOST CHANCE: " + accessRate);
	}
	
}
