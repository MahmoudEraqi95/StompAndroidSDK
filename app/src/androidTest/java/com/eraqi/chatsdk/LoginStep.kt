package com.eraqi.chatsdk

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.eraqi.chatsdk.ui.MainActivity
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.`is`


class LoginStep(private val activityHolder: ActivityHolder) {

    var mainActivity: MainActivity? = null

    @Given("Login Fragment is Shown")
    fun IHaveLoginFragment() {
        activityHolder.launch(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                MainActivity::class.java
            )
        )
    }

    @When("Register Button is Clicked")
    fun registerIsClicked() {
        onView(withId(R.id.register)).perform(click())
    }

    @Then("Error Toast Should Appear")
    fun errorToastShouldAppear() {
        onView(withText(R.string.connection_error))
            .inRoot(withDecorView(not(`is`(mainActivity?.window?.decorView))))
            .check(matches(isDisplayed()))
    }
}
