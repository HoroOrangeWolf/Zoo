package com.minner.michalski.mozdzierz.ozga.zoo.IntegrationIT;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.SectionRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.Path;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathElement;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathElementRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Map.PathRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class MapIT {

    @Autowired
    private MockMvc mockMvc;

    private final static String url = "http://localhost";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private PathElementRepository pathElementRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @AfterEach
    public void clear(){
        pathRepository.deleteAll();
        pathElementRepository.deleteAll();
        sectionRepository.deleteAll();
    }

    @Test
    @Disabled
    void getPath() throws Exception {
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section section2 = new Section("Ptaki", "Papuga ",
                false, 1.f, 1.f, "brak");

        Path path = new Path();

        PathElement pathElement = new PathElement(section,path, false);

        PathElement pathElement2 = new PathElement(section2,path,false);

        pathRepository.save(path);

        sectionRepository.save(section);

        sectionRepository.save(section2);

        pathElementRepository.save(pathElement);
        pathElementRepository.save(pathElement2);


        //when
        ResultActions resultActions = mockMvc.perform(get(url + "/api/v1/map/" + path.getId())).andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();

        //then

        String content = result.getResponse().getContentAsString();

        Path path_from_api = objectMapper.readValue(content, Path.class);

        assertEquals(path, path_from_api);

    }

    @Test
    void setVisited() throws Exception {
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section section2 = new Section("Ptaki", "Papuga ",
                false, 1.f, 1.f, "brak");

        Path path = new Path();

        PathElement pathElement = new PathElement(section,path, false);

        PathElement pathElement2 = new PathElement(section2,path,false);

        pathRepository.save(path);

        sectionRepository.save(section);

        sectionRepository.save(section2);

        pathElementRepository.save(pathElement);
        pathElementRepository.save(pathElement2);


        //when
        ResultActions resultActions = mockMvc.perform(put(url + "/api/v1/map/pathelement/" + pathElement2.getId()).param("visited", Boolean.toString(true))).andExpect(status().isOk());

        //then

        Optional<PathElement> byId = pathElementRepository.findById(pathElement2.getId());

       assertTrue(byId.isPresent());

        PathElement returned = byId.get();

        assertTrue(returned.getIsVisited());
    }

}
