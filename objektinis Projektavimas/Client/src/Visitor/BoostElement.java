package Visitor;

import Mediator.BoostsMediator;

public abstract class BoostElement {
	protected int accessRate;
	private BoostsMediator mediator;
	
	public abstract void boost(ImprovementVisitor visitor);
	public abstract void sendNotification();
	public abstract void receiveNotification(String boostType);
	
	public BoostsMediator getMediator() {
		return mediator;
	}
	
	public void setMediator(BoostsMediator mediator) {
		this.mediator = mediator;
	}
	
	public int getAccessRate() {
		return accessRate;
	}
}