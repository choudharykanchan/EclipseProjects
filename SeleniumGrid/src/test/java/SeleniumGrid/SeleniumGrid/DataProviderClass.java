package SeleniumGrid.SeleniumGrid;


import org.testng.annotations.DataProvider;
public class DataProviderClass {
        @DataProvider(name="username")
        public static Object[][] getDataFromDataprovider(){
            return new Object[][] {
                { "Guru99" },
                { "Krishna"},
                { "Bhupesh" }
            };  
}}

