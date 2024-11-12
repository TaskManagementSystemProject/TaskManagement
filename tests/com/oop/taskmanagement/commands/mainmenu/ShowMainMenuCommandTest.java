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
                #  8. ASSIGNTOMEMBER        #
                #  9. UNASSINGFROMMEMBER    #
                # 10. SHOWTEAMS             #
                # 11. SHOWMEMBERS           #
                # 12. SHOWTEAMMEMBERS       #
                # 13. SHOWTEAMBOARDS        #
                # 14. SHOWTEAMACTIVITY      #
                # 15. SHOWMEMBERACTIVITY    #
                # 16. SHOWBOARDACTIVITY     #
                # 17. CHANGEBUGSTATUS       #
                # 18. CHANGEBUGPRIORITY     #
                # 19. CHANGEBUGSEVERITY     #
                # 20. CHANGESTORYSTATUS     #
                # 21. CHANGESTORYPRIORITY   #
                # 22. CHANGESTORYSIZE       #
                # 23. CHANGEFEEDBACKSTATUS  #
                # 24. CHANGEFEEDBACKRATING  #
                # 25. LISTTASKS             #
                # 26. LISTASSIGNEDTASKS     #
                # 27. LISTBUGS              #
                # 28. LISTFEEDBACKS         #
                # 29. LISTSTORIES           #
                # 30. SHOWMAINMENU          #""".replaceAll("\n",System.lineSeparator());
    }
}
