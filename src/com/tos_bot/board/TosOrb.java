package com.tos_bot.board;

public enum TosOrb {
	WATER_N("10"), WATER_P("11"),
	FIRE_N("20"), FIRE_P("21"),
	WOOD_N("30"), WOOD_P("31"),
	LIGHT_N("40"),LIGHT_P("41"),
	DARK_N("50"),DARK_P("51"),
	HERT_N("60"),HERT_P("61");
	

    private final String value;

    private TosOrb(String value) {
        this.value = value;
    }
    public boolean equalsName(String otherName){
        return (otherName == null)? false:value.equals(otherName);
    }

    public String toString(){
       return value;
    }
}
