

/**
 * DoSearchService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package info.puton.customize.hsb.axis;

    /*
     *  DoSearchService java interface
     */

    public interface DoSearchService {
          

        /**
          * Auto generated method signature
          * 
                    * @param gETDOCLISTSBYDATE0
                
         */

         
                     public GETDOCLISTSBYDATEResponse gETDOCLISTSBYDATE(

                             GETDOCLISTSBYDATE gETDOCLISTSBYDATE0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param gETDOCLISTSBYDATE0
            
          */
        public void startgETDOCLISTSBYDATE(

                GETDOCLISTSBYDATE gETDOCLISTSBYDATE0,

                final DoSearchServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param gETDOCINFOBYUNID2
                
         */

         
                     public GETDOCINFOBYUNIDResponse gETDOCINFOBYUNID(

                             GETDOCINFOBYUNID gETDOCINFOBYUNID2)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param gETDOCINFOBYUNID2
            
          */
        public void startgETDOCINFOBYUNID(

                GETDOCINFOBYUNID gETDOCINFOBYUNID2,

                final DoSearchServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param gETDELECTDOCLISTS4
                
         */

         
                     public GETDELECTDOCLISTSResponse gETDELECTDOCLISTS(

                             GETDELECTDOCLISTS gETDELECTDOCLISTS4)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param gETDELECTDOCLISTS4
            
          */
        public void startgETDELECTDOCLISTS(

                GETDELECTDOCLISTS gETDELECTDOCLISTS4,

                final DoSearchServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    