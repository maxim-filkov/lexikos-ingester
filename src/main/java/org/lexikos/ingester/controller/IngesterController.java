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

package org.lexikos.ingester.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Ingester controller to upload dictionary.
 *
 * @author Maksim Filkov
 */
@Controller
public class IngesterController {

   @Value("${server.dict.upload.folder}")
   private String uploadFolder;

   /**
    * Uploads dictionaries to server and ingests them.
    *
    * @param dict Dictionary.
    * @return All found partially matched entries.
    */
   @RequestMapping(path = "/upload", method = RequestMethod.GET)
   public ResponseEntity uploadDictionary(
      @RequestParam("dict") final MultipartFile dict) {
      if (dict.isEmpty()) {
         return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body("{\"message\":\"Empty file is not allowed\"}");
      }

      try {
         final byte[] bytes = dict.getBytes();
         final Path path = Paths.get(uploadFolder + dict.getOriginalFilename());
         Files.write(path, bytes);
      } catch (final IOException e) {
         throw new RuntimeException(e);
      }
      return ResponseEntity.status(HttpStatus.OK).body("");
   }

}
