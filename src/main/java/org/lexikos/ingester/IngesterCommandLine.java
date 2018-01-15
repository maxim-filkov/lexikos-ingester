/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lexikos.ingester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.lexikos.ingester.model.XmlDictionary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * This Ingester class is to run Ingester application from command line.
 *
 * @author Maksim Filkov
 */
@Service
public class IngesterCommandLine {

   @Value("${translator.http.protocol}")
   private String translatorProtocol;

   @Value("#{environment.TRANSLATOR_HOST}")
   private String translatorHost;

   @Value("#{environment.TRANSLATOR_PORT}")
   private String translatorPort;

   final static Logger logger = Logger.getLogger(IngesterCommandLine.class);

   private static XmlDictionary dictionary;

   public static void main(final String[] argv) throws JAXBException, JsonProcessingException {
      if (argv.length == 0) {
         System.out.println("Define dictionary path");
         System.exit(1);
      }

      readDictionary(argv[0]);
      for (final XmlDictionary.XmlEntry entry : dictionary.getEntries()) {
         try {
            postEntry(entry);
         } catch (final IllegalStateException ex) {
            logger.error(String.format("Failed with error '%s' when adding entry:\n%s", ex.getMessage(), entry));
         }
      }
   }

   private static void readDictionary(final String dictionaryPath) throws JAXBException {
      final JAXBContext jaxbContext = JAXBContext.newInstance();
      final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      dictionary = (XmlDictionary) jaxbUnmarshaller.unmarshal(new File(dictionaryPath));
   }

   private static void postEntry(final XmlDictionary.XmlEntry entry) throws IllegalStateException, JsonProcessingException {
      final Client client = Client.create();
      final WebResource webResource = client.resource("http://localhost:8080/search/v1/translations/enru/");
      final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, entry.toJson());
      if (response.getStatus() != 200) {
         throw new IllegalStateException("Failed : HTTP error code : " + response.getStatus());
      }
   }

}
