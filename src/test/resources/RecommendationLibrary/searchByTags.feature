Feature: As a user I can search a recommendation by tag

    Scenario: user can list added book tags
        Given command add is selected
        When command book is selected
        And book recommendation with author "AuthorTest", title "TitleTest", description "DescriptionTest", isbn "isbnTest" and page count "10" is added
        And command edit is selected
        And command book is selected
        And title "TitleTest" is entered
        And field to edit "6" is entered
        And tag "eka" is entered
        And command search tags is selected
        And field to edit "eka" is entered
        And command list books is selected
        Then app lists a recommendation with author "AuthorTest", title "TitleTest", description "DescriptionTest", isbn "isbnTest", and page count "10"

    Scenario: user can list added video recommendations
        Given command add is selected
        When command video is selected
        And video recommendation with title "TitleTest", description "DescriptionTest" and url "urlTest" is added
        And command edit is selected
        And command video is selected
        And title "TitleTest" is entered
        And field to edit "5" is entered
        And tag "eka" is entered
        And command search tags is selected
        And field to edit "eka" is entered
        And command list videos is selected
        Then app lists a recommendation with title "TitleTest", description "DescriptionTest" and url "urlTest"

    Scenario: user can list added blog recommendations
        Given command add is selected
        When command blog is selected
        And blog recommendation with title "TitleTest", author "AuthorTest", description "DescriptionTest" and url "www.blog.fi" is added
        And command edit is selected
        And command blog is selected
        And title "TitleTest" is entered
        And field to edit "5" is entered
        And tag "eka" is entered
        And command search tags is selected
        And field to edit "eka" is entered
        And command list blogs is selected
        Then app lists a recommendation with title "TitleTest", author "AuthorTest", description "DescriptionTest" and url "www.blog.fi"

    Scenario: user can list added podcast recommendations
        Given command add is selected
        When command podcast is selected
        And podcast recommendation with title "TitleTest", name "TestName", author "AuthorTest" and description "DescriptionTest" is added
        And command edit is selected
        And command podcast is selected
        And title "TitleTest" is entered
        And field to edit "5" is entered
        And tag "eka" is entered
        And command search tags is selected
        And field to edit "eka" is entered
        And command list podcasts is selected
        Then app lists a podcast recommendation with title "TitleTest", name "TestName", author "AuthorTest" and description "DescriptionTest"
