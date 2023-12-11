Feature: Test Button Click
  """this is just a test feature to make sure cucmber is usable"""

  Scenario : Just Click the Register Button Without Adding a Phone Number
    Given Login Fragment is Shown
    When Register Button is Clicked
    Then Error Toast Should Appear


