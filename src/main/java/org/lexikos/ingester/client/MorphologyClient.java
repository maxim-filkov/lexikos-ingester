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

package org.lexikos.ingester.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This client works with Morpho micro-service through REST APIs.
 *
 * @author Maksim Filkov
 */
@Service
public class MorphologyClient {

   @Value("${morpho.part.of.speech.url}")
   private String morphoUrl;

   @Value("#{environment.MORPHO_PORT_8081_TCP_ADDR}")
   private String morphoHost;

   @Value("#{environment.MORPHO_PORT_8081_TCP_PORT}")
   private String morphoPort;

   @Resource
   private RestTemplate phpMorphy;

   public List<String> findPartsOfSpeech(final String phrase, final String language) {
      final String url = MessageFormat.format(morphoUrl, morphoHost, morphoPort, phrase, toPhpMorphyLanguage(language));
      final ArrayList<String> partsOfSpeech = phpMorphy.getForObject(url, ArrayList.class);
      return partsOfSpeech;
   }

   private String toPhpMorphyLanguage(final String language) {
      return language + "_" + language.toUpperCase();
   }

}
