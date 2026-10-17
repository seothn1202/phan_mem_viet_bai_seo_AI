package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ConnectionStatus
import com.example.data.MockRepository
import com.example.data.WordPressSite
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("WP SEO Studio", appName)
  }

  @Test
  fun `verify mock repository 12 sites and emergency stop`() {
    val repo = MockRepository()
    val initialSites = repo.sites.value
    assertTrue("Should have at least 12 WordPress websites", initialSites.size >= 12)

    // Test Emergency Stop
    assertEquals(false, repo.automationConfig.value.isEmergencyStop)
    repo.toggleEmergencyStop()
    assertEquals(true, repo.automationConfig.value.isEmergencyStop)

    // Test Adding new site
    val newSite = WordPressSite(
      id = "site_test_99",
      name = "Test Site VN",
      url = "https://testsite.vn",
      username = "tester",
      status = ConnectionStatus.ONLINE
    )
    repo.addWebsite(newSite)
    assertEquals(initialSites.size + 1, repo.sites.value.size)
    assertNotNull(repo.sites.value.find { it.id == "site_test_99" })

    // Test Analyze & Cluster Topic
    val newKeywords = repo.analyzeAndClusterTopic("du lịch đà nẵng", "VN")
    assertTrue(newKeywords.isNotEmpty())
    assertTrue(repo.keywords.value.any { it.keyword.contains("du lịch đà nẵng") })

    // Test Budget update
    repo.updateBudgetSettings(100.0, 5.0, 90, true, "Gemini 1.5 Flash")
    assertEquals(100.0, repo.costSummary.value.budgetCapUsd, 0.01)
    assertEquals("Gemini 1.5 Flash", repo.costSummary.value.topModel)
    assertEquals(100.0, repo.automationConfig.value.monthlyBudgetCapUsd, 0.01)
  }
}
