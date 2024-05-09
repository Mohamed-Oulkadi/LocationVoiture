/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrental;

/**
 *
 * @author NR
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CarHandler extends DefaultHandler {
    private Connection con;
    private boolean bRegNum, bBrand, bModel, bStatus, bPrice;
    private String regNum, brand, model, status;
    private int price;

    public CarHandler(Connection con) {
        this.con = con;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Car")) {
            regNum = attributes.getValue("RegNum");
        } else if (qName.equalsIgnoreCase("Brand")) {
            bBrand = true;
        } else if (qName.equalsIgnoreCase("Model")) {
            bModel = true;
        } else if (qName.equalsIgnoreCase("Status")) {
            bStatus = true;
        } else if (qName.equalsIgnoreCase("Price")) {
            bPrice = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Car")) {
            try {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO APP.CARTBL (CaReg, Brand, Model, Status, Price) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, regNum);
                stmt.setString(2, brand);
                stmt.setString(3, model);
                stmt.setString(4, status);
                stmt.setInt(5, price);
                stmt.executeUpdate();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bBrand) {
            brand = new String(ch, start, length);
            bBrand = false;
        } else if (bModel) {
            model = new String(ch, start, length);
            bModel = false;
        } else if (bStatus) {
            status = new String(ch, start, length);
            bStatus = false;
        } else if (bPrice) {
            price = Integer.parseInt(new String(ch, start, length));
            bPrice = false;
        }
    }
}

