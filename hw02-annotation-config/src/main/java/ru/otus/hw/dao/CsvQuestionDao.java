package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository(value = "questionDao")
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        if (fileNameProvider.getTestFileName() == null || fileNameProvider.getTestFileName().isEmpty()) {
            throw new QuestionReadException("Resource file name is empty!");
        }

        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStreamReader streamReader = new InputStreamReader(
                Objects.requireNonNull(classLoader.getResourceAsStream(fileNameProvider.getTestFileName())),
                StandardCharsets.UTF_8);
        ) {

            CsvToBean csvToBean = new CsvToBeanBuilder(streamReader)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .withSkipLines(1).build();
            List<QuestionDto> list = csvToBean.parse();
            return list.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());

        } catch (IOException e) {
            throw new QuestionReadException(e.getMessage());
        }
    }
}
