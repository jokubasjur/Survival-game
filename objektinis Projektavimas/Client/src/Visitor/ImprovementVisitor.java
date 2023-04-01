package Visitor;

public interface ImprovementVisitor {
	public void activateLowBoost(LowBoost b);
	public void activateMidBoost(MidBoost b);
	public void activateHighBoost(HighBoost b);
}
