package it.polito.ezgas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EZGasEntityTests {
	
    public User createUserTestSuite(){
        return new User("userName", "password", "email", 2);
    }
    
    public PriceReport createPriceReportTestSuite(){
        return new PriceReport(createUserTestSuite(), 0.0, 0.0, 0.0, 0.0);

       }
	
    public GasStation createGasStationTestSuite(){
        //return new GasStation();
        return new GasStation("gasStationName", "gasStationAddress", true, true, true, true, true, "carSharing", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4, "reportTimestamp", 1.0);
       }
    
	//GAS STATION UNIT TESTS : 
    @Test
    public void getGasStationId() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNull("gs id is null", gs.getGasStationId());
    
    } 

    @Test
    public void setGasStationId() {
     Integer id= 0;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setGasStationId(id);
     assertNotNull("gs id is not null", gs.getGasStationId());
    
    } 
    
    @Test
    public void getGasStationName() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs name is not null", gs.getGasStationName());
     assertEquals(gs.getGasStationName(), "gasStationName");
    } 
    
    @Test
    public void setGasStationName() {
     String new_name = "new_name";
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setGasStationName(new_name);
     assertNotNull("gs name is not null", gs.getGasStationName());
     assertEquals(gs.getGasStationName(), new_name);
    } 
    
    @Test
    public void getGasStationAddress() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs address is not null", gs.getGasStationAddress());
     assertEquals(gs.getGasStationAddress(), "gasStationAddress");
    } 
    
    @Test
    public void setGasStationAddress() {
     String new_name = "new_gasStationAddress";
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setGasStationAddress(new_name);
     assertNotNull("gs name is not null", gs.getGasStationAddress());
     assertEquals(gs.getGasStationAddress(), new_name);
    } 
    
    @Test
    public void getReportDependability() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs report dependability is not null", gs.getReportDependability());
     assertTrue(gs.getReportDependability() == 1.0);
    } 
    
    @Test
    public void setReportDependability() {
     double new_reportdependability = 5.0;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setReportDependability(new_reportdependability);
     assertNotNull("gs report dependability is not null", gs.getGasStationAddress());
     assertTrue(gs.getReportDependability() == 5.0);
    } 
    
    @Test
    public void getReportUser() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs report user is not null", gs.getReportUser());
     assertTrue(gs.getReportUser() == 4);
    } 
    
    @Test
    public void setReportUser() {
     Integer new_reportuser = 5;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setReportUser(new_reportuser);
     assertNotNull("gs report user is not null", gs.getReportUser());
     assertTrue(gs.getReportUser() == 5);
    } 
    
    @Test
    public void getReportTimestamp() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs report timestamp is not null", gs.getReportUser());
     assertEquals(gs.getReportTimestamp(), "reportTimestamp");
    } 
    
    @Test
    public void setReportTimestamp() {
     String new_reporttimestamp = "new_report_timestamp";
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setReportTimestamp(new_reporttimestamp);
     assertNotNull("gs report timestamp is not null", gs.getReportUser());
     assertEquals(gs.getReportTimestamp(), new_reporttimestamp);
    } 
    
    @Test
    public void getHasDiesel() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs hasDiesel is not null", gs.getHasDiesel());
     assertEquals(gs.getHasDiesel(), true);
    } 
    
    @Test
    public void setHasDiesel() {
     boolean new_hasdiesel = false;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setHasDiesel(new_hasdiesel);
     assertNotNull("gs hasDiesel is not null", gs.getHasDiesel());
     assertEquals(gs.getHasDiesel(), new_hasdiesel);
    } 
    
    @Test
    public void getHasGas() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs hasGas is not null", gs.getHasGas());
     assertEquals(gs.getHasGas(), true);
    } 
    
    @Test
    public void setHasGas() {
     boolean new_hasGas = false;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setHasGas(new_hasGas);
     assertNotNull("gs hasGas is not null", gs.getHasGas());
     assertEquals(gs.getHasGas(), new_hasGas);
    } 
    
    @Test
    public void getHasMethane() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs hasMethane is not null", gs.getHasMethane());
     assertEquals(gs.getHasMethane(), true);
    } 
    
    @Test
    public void setHasMethane() {
     boolean new_hasMethane = false;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setHasMethane(new_hasMethane);
     assertNotNull("gs hasMethane is not null", gs.getHasMethane());
     assertEquals(gs.getHasMethane(), new_hasMethane);
    } 
    
    @Test
    public void getHasSuper() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs hassuper is not null", gs.getHasSuper());
     assertEquals(gs.getHasSuper(), true);
    } 
    
    @Test
    public void setHasSuper() {
     boolean new_hassuper = false;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setHasSuper(new_hassuper);
     assertNotNull("gs hassuper is not null", gs.getHasSuper());
     assertEquals(gs.getHasSuper(), new_hassuper);
    } 
    
    @Test
    public void getHasSuperPlus() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs hassuperPlus is not null", gs.getHasSuperPlus());
     assertEquals(gs.getHasSuperPlus(), true);
    } 
    
    @Test
    public void setHasSuperPlus() {
     boolean new_hassuperPlus = false;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setHasSuperPlus(new_hassuperPlus);
     assertNotNull("gs hassuperPlus is not null", gs.getHasSuperPlus());
     assertEquals(gs.getHasSuperPlus(), new_hassuperPlus);
    } 
    
    @Test
    public void getGasPrice() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs price is not null", gs.getGasPrice());
     assertTrue(gs.getGasPrice() == 0.0);
    } 
    
    @Test
    public void setGasPrice() {
     double new_gasprice = 1.7;
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setGasPrice(new_gasprice);
     assertNotNull("gs gasprice is not null", gs.getGasPrice());
     assertTrue(gs.getGasPrice() == new_gasprice);
    } 
    
    @Test
    public void getUser() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNull("gs has no user : getuser returns null", gs.getUser());
    } 
    
    @Test
    public void setUser() {
     User new_user = new User();
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setUser(new_user);
     assertNotNull("gs has a user", gs.getUser());
     assertTrue(gs.getUser() == new_user);
    } 
    
    @Test
    public void getCarSharing() {
     GasStation gs =null;
     gs = createGasStationTestSuite();
     assertNotNull("gs car sharing is not null", gs.getCarSharing());
     assertEquals(gs.getCarSharing(), "carSharing");
    } 
    
    @Test
    public void setCarSharing() {
     String new_name = "new_carsharing";
     GasStation gs =null;
     gs = createGasStationTestSuite();
     gs.setCarSharing(new_name);
     assertNotNull("gs car sharing is not null", gs.getCarSharing());
     assertEquals(gs.getCarSharing(), new_name);
    } 
    
	//USER UNIT TESTS : 
    @Test
    public void getUserId() {
    	User user = null;
    	user = createUserTestSuite();
        assertNull("user id is null", user.getUserId());
    }
    
    @Test
    public void setUserId() {
        Integer new_id = 1;
        User user = null;
        user = createUserTestSuite();
        user.setUserId(new_id);
        assertNotNull("user id is not null", user.getUserId());
        assertEquals(user.getUserId(), new_id);
    }
    
    @Test
    public void getUserName() {
    	User user = null;
    	user = createUserTestSuite();
        assertNotNull("user name is not null", user.getUserName());
    }
    
    @Test
    public void setUserName() {
        String new_name = "new_name";
        User user = null;
        user = createUserTestSuite();
        user.setUserName(new_name);
        assertNotNull("user name is not null", user.getUserName());
        assertEquals(user.getUserName(), new_name);
    }
    
    @Test
    public void getPassword() {
    	User user = null;
    	user = createUserTestSuite();
        assertNotNull("user password is not null", user.getPassword());
    }
    
    @Test
    public void setPassword() {
        String new_password = "new_password";
        User user = null;
        user = createUserTestSuite();
        user.setPassword(new_password);
        assertNotNull("user password is not null", user.getPassword());
        assertEquals(user.getPassword(), new_password);
    }
    
    @Test
    public void getEmail() {
    	User user = null;
    	user = createUserTestSuite();
        assertNotNull("user email is not null", user.getEmail());
    }
    
    @Test
    public void setEmail() {
        String new_email = "new_password";
        User user = null;
        user = createUserTestSuite();
        user.setEmail(new_email);
        assertNotNull("user email is not null", user.getEmail());
        assertEquals(user.getEmail(), new_email);
    }
    
    @Test
    public void getReputation() {
    	User user = null;
    	user = createUserTestSuite();
        assertNotNull("user reputation is not null", user.getReputation());
    }
    
    @Test
    public void setReputation() {
        Integer new_reputation = 3;
        User user = null;
        user = createUserTestSuite();
        user.setReputation(new_reputation);
        assertNotNull("user reputation is not null", user.getReputation());
        assertEquals(user.getReputation(), new_reputation);
    }
    
    @Test
    public void getAdmin() {
    	User user = null;
    	user = createUserTestSuite();
        assertNull("user admin is null", user.getAdmin());
    }
    
    @Test
    public void setAdmin() {
        boolean new_admin = false;
        User user = null;
        user = createUserTestSuite();
        user.setAdmin(new_admin);
        assertNotNull("user admin is not null", user.getAdmin());
        assertEquals(user.getAdmin(), new_admin);
    }
    
    //PRICEREPORT TESTS : 
    @Test
    public void getPriceReportUser() {
    	PriceReport priceReport = null;
    	priceReport = createPriceReportTestSuite();
        assertNotNull("PriceReport user isnot null", priceReport.getUser());
    }
    
    @Test
    public void setPriceReportUser() {
        User new_user = createUserTestSuite();
        PriceReport priceReport = null;
        priceReport = createPriceReportTestSuite();
        priceReport.setUser(new_user);
        assertNotNull("user is not null", priceReport.getUser());
        assertEquals(priceReport.getUser(), new_user);
    }
    
    @Test
    public void getDieselPrice() {
    	PriceReport priceReport = null;
    	priceReport = createPriceReportTestSuite();
        assertNotNull("PriceReport diesel price is not null", priceReport.getDieselPrice());
    }
    
    @Test
    public void setDieselPrice() {
        double new_diesel_price = 3.1;
        PriceReport priceReport = null;
        priceReport = createPriceReportTestSuite();
        priceReport.setDieselPrice(new_diesel_price);
        assertNotNull("Pricereport diesel price is not null", priceReport.getDieselPrice());
        assertTrue(priceReport.getDieselPrice() == new_diesel_price);
    }
    
    @Test
    public void getSuperPrice() {
    	PriceReport priceReport = null;
    	priceReport = createPriceReportTestSuite();
        assertNotNull("PriceReport super price is not null", priceReport.getSuperPrice());
    }
    
    @Test
    public void setSuperPrice() {
        double new_super_price = 1.2;
        PriceReport priceReport = null;
        priceReport = createPriceReportTestSuite();
        priceReport.setSuperPrice(new_super_price);
        assertNotNull("Pricereport super price is not null", priceReport.getSuperPrice());
        assertTrue(priceReport.getSuperPrice() == new_super_price);
    }
    
    @Test
    public void getSuperPlusPrice() {
    	PriceReport priceReport = null;
    	priceReport = createPriceReportTestSuite();
        assertNotNull("PriceReport super plus price is not null", priceReport.getSuperPlusPrice());
    }
    
    @Test
    public void setSuperPlusPrice() {
        double new_super_plus_price = 1.2;
        PriceReport priceReport = null;
        priceReport = createPriceReportTestSuite();
        priceReport.setSuperPlusPrice(new_super_plus_price);
        assertNotNull("Pricereport super plus price is not null", priceReport.getSuperPlusPrice());
        assertTrue(priceReport.getSuperPlusPrice() == new_super_plus_price);
    }
    
    @Test
    public void getPriceReportGasPrice() {
    	PriceReport priceReport = null;
    	priceReport = createPriceReportTestSuite();
        assertNotNull("PriceReport gas price is not null", priceReport.getGasPrice());
    }
    
    @Test
    public void setPriceReportGasPrice() {
        double new_gas_price = 1.2;
        PriceReport priceReport = null;
        priceReport = createPriceReportTestSuite();
        priceReport.setSuperPlusPrice(new_gas_price);
        assertNotNull("Pricereport gas price is not null", priceReport.getGasPrice());
        assertTrue(priceReport.getSuperPlusPrice() == new_gas_price);
    }
    
    @Test
    public void getPriceReportId() {
    	PriceReport priceReport = null;
    	priceReport = createPriceReportTestSuite();
        assertNull("PriceReport has no id", priceReport.getPriceReportId());
    }
    
    @Test
    public void setPriceReportId() {
        Integer new_price_report_id = 3;
        PriceReport priceReport = null;
        priceReport = createPriceReportTestSuite();
        priceReport.setPriceReportId(new_price_report_id);
        assertNotNull("Pricereport id is not null", priceReport.getPriceReportId());
        assertTrue(priceReport.getPriceReportId() == new_price_report_id);
    }

}
