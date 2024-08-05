package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.repository.IssueRepository
import io.mockk.mockk
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
internal class IssueDataSourceImplTest{
    @Autowired
    private lateinit var issueDataSource: IssueDataSourceImpl
    @Test
    fun `should provide a collection of issues`(){
        //given

        //when
        val issues=issueDataSource.retrieveIssues()
        //then
        println("issues-------->$issues")
        assertThat(issues).isNotEmpty()
        assertThat(issues.size).isGreaterThanOrEqualTo(3)
    }
}