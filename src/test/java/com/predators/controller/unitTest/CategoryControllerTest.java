package com.predators.controller.unitTest;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.predators.controller.CategoryController;
import com.predators.entity.Category;
import com.predators.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(new Category(1L, "GARDEN_TOOLS",null
        ), new Category(2L, "PLANTS",null));
        when(categoryService.getAll()).thenReturn(categories);

        mockMvc.perform(get("/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categories)));
    }

    @Test
    void getCategoryById() throws Exception {
        Category category = new Category(1L, "GARDEN_TOOLS",null);
        when(categoryService.getById(1L)).thenReturn(category);

        mockMvc.perform(get("/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void createCategory() throws Exception {
        Category category = new Category(1L, "GARDEN_TOOLS",null);
        when(categoryService.create(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @Test
    void deleteCategory() throws Exception {
        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete("/v1/categories/1"))
                .andExpect(status().isNoContent());
    }
}
