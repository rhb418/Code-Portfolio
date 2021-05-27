
3 Scenarios:

*   Error producing:
    *   Model clipping -> Collision test:
        *   Test the case when player moves the characters (with relatively large speed), whether the player will pass through/collides the obstacles.
        *   How: check whether the speed has been changed in the testing process.
    *   Use the items (Metroid) that has been used up/blocked items:
        *   Test whether the player can use the items that has been used up/blocked items.
        *   How: determine whether everything remains the same.
    *   Space Invaders: Camera Shifting:
        *   Test the case that the player keep moving to indirection.
        *   How: keep tracking the position of the camera and the position of the player, make sure that their relative positions remain unchanged.
    *   Mario: keep jumping:
        *   Test keep pressing spacebar in the mario game:
        *   How: make sure mario only jump 1/2 times instead of keep jumping while he was off the ground.

*   UI testing:
    *   Text testing:
        *   Press different buttons in the dialog box to see whether it will generate different response.
        *   How: check the string in the current dialog box to see whether it matches the expected one.
    *   Character display:
        *   Test whether Mario is always been displayed in the screen field.
        *   How: For instance, while keep moving Mario, place a button on Mario (set the button the same position as Mario's, and the button's layer should below the Mario's to make sure that the button is invisible) try to click that button, if it fails, then Mario is not on the screen at that moment.
    *   Button test:
        *   Test: click certain buttons to see the results:
        *   How: for instance, whether clicking "next level" will make the game switch to the next level
    *   Entities test:
        *   Test whether a entity disappear after it finished its "mission":
        *   How: check the pixel's RGB at certain position.

*   Model & Action testing & Contoll:
    *   Multi key strokes at the same time:
        *   Even though the player press the invalid key at the moment, the game should not abort.
        *   How: check whether the game run properly after multiple (invalid) keystrokes (maybe multi key strokes at the same time).
    *   Config File reading:
        *   No corruptions during the reading process.
        *   How: Detect whether the game has corrupted during the invalid file reading process.
    *   Level completion:
        *   Check whether the player is asked to play the next level after he complete the current ones.
        *   How: Check notification (text box) and try pressing "next level"
    *   Multi buffs:
        *   Detect whether the model acts correctly when the player gets multiple buffs in a single frame.
        *   How: Check whether the state of the player is expected after he/she gets these buffs.
