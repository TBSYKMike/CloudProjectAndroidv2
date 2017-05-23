package com.example.miketest.cloudprojectandroid;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlSerializer;

/**
 * Created by Henrik on 2017-05-19.
 */

public class WebServiceConnector extends AsyncTask<String, Void, String> { //Only for the testing. Will be removed

    // private static final String METHOD_NAME = "updateExistingServiceItem";
    // private static final String NAMESPACE = "http://tempuri.org/";//com.service.

    //private static final String URL = "http://192.168.1.219:53087/Service1.svc?wsdl";//h

    //final String SOAP_ACTION = "http://tempuri.org/IService1/updateExistingServiceItem";//http://tempuri.org/IService1/HelloWorld

    StringBuilder sb;

    @Override
    protected String doInBackground(String... params) {
        System.out.println("xxx Webservice is starting ");
        //call();
        // test2();
        //refreshUsers();
        Call3(66, 5);
        return null;
    }

    private XmlSerializer writer;

    public final String SOAP_ACTION = "http://tempuri.org/IService1/calculate";

    public final String OPERATION_NAME = "calculate";

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
//
    public final String SOAP_ADDRESS = "http://10.0.2.2//Service1.svc";

    public String Call3(int a, int b) {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("a");
        pi.setValue(a);
        pi.setType(Integer.class);
        request.addProperty(pi);
        pi = new PropertyInfo();
        pi.setName("b");
        pi.setValue(b);
        pi.setType(Integer.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response = null;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
            System.out.println("Response:   " + response);
        } catch (Exception exception) {
            response = exception.toString();
        }
        return response.toString();
    }



}

  /*  public void call() {
        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
           // request.addProperty("Name", "Qing");
            System.out.println("1");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            System.out.println("2");
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            System.out.println("3");
            androidHttpTransport.call(SOAP_ACTION, envelope);
            System.out.println("4");
            SoapPrimitive result = (SoapPrimitive)envelope.getResponse();
            //to get the data
            System.out.println("5");
            String resultData = result.toString();
            // 0 is the first object of data
            sb.append(resultData + "\n");
            System.out.println("xxx" + resultData + "\n");
            System.out.println("xxx" + resultData);
        } catch (Exception e) {

        }

    }



    private void refreshUsers() {
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            URI uri = new URI("http://194.47.41.172:53087/Service1.svc?wsdl");

            HttpGet httpget = new HttpGet(uri + "/HelloWorld");
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("Content-type", "application/json; charset=utf-8");

            HttpResponse response = httpClient.execute(httpget);
            HttpEntity responseEntity = response.getEntity();

            long intCount = responseEntity.getContentLength();
            char[] buffer = new char[(int)intCount];
            InputStream stream = responseEntity.getContent();
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            try
            {
                reader.read(buffer);
                String str = new String(buffer);
                System.out.println("result  " + str);
        }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            stream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void test2(){

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER12);
        System.out.println("1");
        envelope.setOutputSoapObject(request);
        System.out.println("2");
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        System.out.println("3");
        try {
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            androidHttpTransport.debug = true;
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            System.out.println("10");
            System.out.println("result  " + resultsRequestSOAP.toString());
            System.out.println("11");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("7");
    }
*/



