package com.oop.taskmanagement.commands.mainmenu;

import com.oop.taskmanagement.commands.contracts.Command;
import com.oop.taskmanagement.exceptions.InvalidUserInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShowMainMenuCommandTest {

    private Command showMainMenuCommand;


    @BeforeEach
    public void initialize() {
        showMainMenuCommand = new ShowMainMenuCommand();
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentsCountDiffer(){
        // Arrange, Act, Assert
        Assertions.assertThrows(InvalidUserInputException.class, () -> showMainMenuCommand.execute(List.of("Invalid")));
    }

    @Test
    public void execute_Should_ReturnEntireMenu_When_ArgumentsValid(){
        // Arrange
        String expectedOutput = validMainMenu();

        // Act
        String actualOutput = showMainMenuCommand.execute(List.of());

        // Assert
        Assertions.assertEquals(expectedOutput, actualOutput);
    }


    private String validMainMenu(){
        return """
                #############################
                #       Commands Menu       #
                #  1. CREATETEAM            #
                #  2. CREATEMEMBER          #
                #  3. CREATEBOARDINTEAM     #
                #  4. CREATEBUGINBOARD      #
                #  5. CREATESTORYINBOARD    #
                #  6. CREATEFEEDBACKINBOARD #
                #  7. ADDPERSONTOTEAM       #
                #  8. ADDCOMMENTTOTASK      #
                #  9. ASSIGNTOMEMBER        #
                # 10. UNASSIGNFROMMEMBER    #
                # 11. SHOWTEAMS             #
                # 12. SHOWMEMBERS           #
                # 13. SHOWTEAMMEMBERS       #
                # 14. SHOWTEAMBOARDS        #
                # 15. SHOWTEAMACTIVITY      #
                # 16. SHOWMEMBERACTIVITY    #
                # 17. SHOWBOARDACTIVITY     #
                # 18. CHANGEBUGSTATUS       #
                # 19. CHANGEBUGPRIORITY     #
                # 20. CHANGEBUGSEVERITY     #
                # 21. CHANGESTORYSTATUS     #
                # 22. CHANGESTORYPRIORITY   #
                # 23. CHANGESTORYSIZE       #
                # 24. CHANGEFEEDBACKSTATUS  #
                # 25. CHANGEFEEDBACKRATING  #
                # 26. LISTTASKS             #
                # 27. LISTASSIGNEDTASKS     #
                # 28. LISTBUGS              #
                # 29. LISTFEEDBACKS         #
                # 30. LISTSTORIES           #
                # 31. SHOWMAINMENU          #""".replaceAll("\n",System.lineSeparator());
    }
}
