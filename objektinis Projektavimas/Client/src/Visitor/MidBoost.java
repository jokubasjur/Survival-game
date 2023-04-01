package Visitor;

import Mediator.BoostsMediator;

public class MidBoost extends BoostElement{
	
	public MidBoost(BoostsMediator mediator) {
		setMediator(mediator);
		this.accessRate = 35;
	}
	
	@Override
	public void boost(ImprovementVisitor visitor) {
		visitor.activateMidBoost(this);
		sendNotification();
	}

	@Override
	public void sendNotification() {
		if (getMediator() != null) {
			getMediator().broadcast("mid");
		}
	}

	@Override
	public void receiveNotification(String boostType) {
		switch (boostType) {
			case "low":
				accessRate = accessRate + 10;
				break;
			case "mid":
				accessRate = accessRate - 10;
				break;
			case "high":
				accessRate = accessRate + 5;
				break;
		}
		
		if (accessRate < 10) {
			accessRate = 10;
		}
		System.out.println("MID BOOST CHANCE: " + accessRate);
	}
}
