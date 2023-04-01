package Builder;

public class PlagueDoctor extends Hero {
	
	public String toString() {
        return "Plague Doctor is ready!"+
                "\nDefense: "+getDefense()+
                "\nDamage: "+getDamage()+
                "\nHeadgear: "+getHeadGear();
    }
}
