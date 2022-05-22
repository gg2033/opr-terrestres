package com.losilegales.oprterrestres.enums;

public enum TipoTag {
	NORMAL, PELIGROSO, ANIMAL, ESTANDAR, DELICADO;
	

	public static TipoTag fromString( String value )
    {
        if ( "normal".equals( value )  ) 
        {
            return NORMAL;
        }
        else if ( "normal".equals( value )  ) 
                {
                    return NORMAL;
                }
        else if ( "peligroso".equals( value )  ) 
                {
                    return NORMAL;
                }
        if ( "animal".equals( value )  ) 
                {
                    return NORMAL;
                }
        if ( "estandar".equals( value )  ) 
                {
                    return NORMAL;
                }
        if ( "delicado".equals( value )  ) 
                {
                    return NORMAL;
                }
		return NORMAL;
        
        //etc
    }

}
