/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author dohoangan
 */
public class test {

    public static void main(String[] args) {
        
        /*
        ApplicationContext appContext = new ClassPathXmlApplicationContext("springs/cat21.xml");
        IDataField data = (IDataField) appContext.getBean("dataSourceIdentification");
        
        Acas ac = Acas.AcasNotOperational;
        System.out.println("Code:" + Acas.AcasNotOperational.getCode());
        System.out.println("Code:" + Acas.AcasOperational.getCode());
        System.out.println("Code:" + Acas.Invalid.getCode());
        System.out.println("Code:" + Acas.Unknow.getCode());
        
        System.out.println("2^24=" + Math.pow(2, 24) + "Hex:" + Integer.toHexString(11305708));
        */
        
        /*
        System.out.println(Float.MAX_VALUE + " --- " + Float.MIN_VALUE);
        System.out.println("Float: " + ((float) (180/Math.pow(2, 23))));
        System.out.println("Float: " + ((float) (2.145767 * 0.00001)));
        System.out.println("Double: " + (180/Math.pow(2, 23)));
        System.out.println("SHORT:" + Short.MAX_VALUE + " --- " + Short.MIN_VALUE);
        */
        System.out.println("-" + Integer.toBinaryString(-1));
        System.out.println(Integer.reverse(-1));
        
        Integer i = new Integer(100);
        kiss(i);
        System.out.println((Integer)i);
        
    }
    
    public static void kiss(Object i) {
        i = 150;
    }
}
