Feature: Profile analysis

  Background:
    Given 'testUser' has a collection of games described in: 'testUserCollection.xml'
    And BGG XML API can provide details for games:
      | id     | details file            |
      | 192458 | game_192458_details.xml |
      | 119337 | game_119337_details.xml |

  Scenario: data ready

  data is instantly available from BGG

    Given BGG XML API is ready to serve profile information for user 'testUser'
    When profile information is requested for 'testUser'
    Then the result is 'testUser.json'

  Scenario: data available after retries

  BGG accepts user info request, but it cannot provide the data instantly

    Given BGG XML API can provide information about user 'testUser' after 3 tries
    When profile information is requested for 'testUser'
    Then the result is 'testUser.json'
