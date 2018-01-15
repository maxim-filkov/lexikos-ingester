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

package org.lexikos.ingester.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * A class describing typical dictionary.
 *
 * @author Maksim Filkov
 */
@Data
@XmlRootElement(name = "dictionary")
public class XmlDictionary {

   Set<XmlEntry> entries;

   @Data
   public static class XmlEntry {

      @XmlElement
      private String pronunciation;

      @XmlElement
      private String sourcePhrase;

      @XmlElement
      private String targetPhrase;

      @XmlElement
      private String partOfSpeech;

      @XmlElement
      private String commentary;

      public String toJson() {
         final ObjectMapper mapper = new ObjectMapper();
         mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
         try {
            return mapper.writeValueAsString(this);
         } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
         }
      }

   }

}