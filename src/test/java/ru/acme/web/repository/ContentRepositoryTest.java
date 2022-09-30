package ru.acme.web.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.acme.web.model.Content;

import java.util.List;
import java.util.Optional;

class ContentRepositoryTest extends AbstractRepository {

    @Autowired
    ContentRepository repository;

    @Test
    void findAllByShowIsTrueAndMenuNotNullOrderByPriorityDesc() {
        List<Content> sortedFromDb = repository.findAllForMain();
        List<Content> allContent = repository.findAll();
        List<Content> sortedInMem = allContent.stream()
                .filter(Content::isShow)
                .filter(content -> content.getMenuId() != null)
                .sorted((o1, o2) -> o2.getPriority().compareTo(o1.getPriority())).toList();
        // Content can be sorted different by other fields
        Assertions.assertEquals(sortedInMem.size(), sortedFromDb.size());
        Assertions.assertTrue(sortedInMem.containsAll(sortedFromDb));
        for (int i = 0; i < sortedInMem.size(); i++) {
            Assertions.assertEquals(sortedInMem.get(i).getPriority(), sortedFromDb.get(i).getPriority());

        }
    }

    @Test
    void deleteByIds() {
        Long id = 100_002L;
        Optional<Content> content = repository.findById(id);
        Assertions.assertTrue(content.isPresent());
        Assertions.assertEquals(id, content.get().getId());

        List<Long> ids2delete = List.of(content.get().getId());
        repository.deleteAllById(ids2delete);
        content = repository.findById(id);
        Assertions.assertTrue(content.isEmpty());
    }

    @Test
    void deleteById() {
        Long id = 100_002L;
        Optional<Content> content = repository.findById(id);
        Assertions.assertTrue(content.isPresent());
        Assertions.assertEquals(id, content.get().getId());

        repository.deleteById(id);
        content = repository.findById(id);
        Assertions.assertTrue(content.isEmpty());
    }

    @Test
    void save() {
        Long id = 100_002L;
        Optional<Content> content = repository.findById(id);
        Assertions.assertTrue(content.isPresent());
        Assertions.assertEquals(id, content.get().getId());

        Content newContent = content.get();
        newContent.setId(null);
        newContent.setContent("newContent");

        int initSize = repository.findAll().size();
        repository.save(newContent);
        int newSize = repository.findAll().size();
        Assertions.assertEquals(newSize, initSize + 1);
    }
}