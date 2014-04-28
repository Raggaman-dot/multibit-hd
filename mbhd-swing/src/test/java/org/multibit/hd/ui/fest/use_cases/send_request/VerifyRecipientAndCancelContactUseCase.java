package org.multibit.hd.ui.fest.use_cases.send_request;

import org.fest.swing.fixture.FrameFixture;
import org.multibit.hd.ui.fest.use_cases.AbstractFestUseCase;
import org.multibit.hd.ui.languages.MessageKey;
import org.multibit.hd.ui.views.themes.Themes;

import java.util.Map;

/**
 * <p>Use case to provide the following to FEST testing:</p>
 * <ul>
 * <li>Verify the "send/receive" screen recipient</li>
 * </ul>
 * <p>Requires the "send/receive" screen to be showing</p>
 *
 * @since 0.0.1
 *  
 */
public class VerifyRecipientAndCancelContactUseCase extends AbstractFestUseCase {

  public VerifyRecipientAndCancelContactUseCase(FrameFixture window) {
    super(window);
  }

  @Override
  public void execute(Map<String, Object> parameters) {

    // Click on Send
    window
      .button(MessageKey.SEND.getKey())
      .click();

    // Verify the wizard appears
    window
      .label(MessageKey.SEND_BITCOIN_TITLE.getKey());

    // Verify buttons
    window
      .button(MessageKey.NEXT.getKey())
      .requireVisible()
      .requireDisabled();

    window
      .button(MessageKey.CANCEL.getKey())
      .requireVisible()
      .requireEnabled();

    // Use a public domain standard address
    verifyBitcoinAddressField("", false);
    verifyBitcoinAddressField(" ", false);
    verifyBitcoinAddressField("AhN", false);
    verifyBitcoinAddressField("AhN6rPdrMuKBGFDKR1k9A8SCLYaNgXhty", false);
    verifyBitcoinAddressField("1AhN6rPdrMuKBGFDKR1k9A8SCLYaNgXht", false);
    verifyBitcoinAddressField("1AhN6rPdrMuKBGFDKR1k9A8SCLYa", false);
    verifyBitcoinAddressField("1AhN6rPdrMuKBGFDk9A8SCLYaNgXhty", false);

    // Use a public domain P2SH address
    verifyBitcoinAddressField("35b9vsyH1KoFT5a5KtrKusaCcPLkiSo1tU", true);
    verifyBitcoinAddressField("35b9vsyH1KoFT5a5KtrKusaCcPLkiSo1t", false);

    // Set it to the MultiBit address
    verifyBitcoinAddressField("1AhN6rPdrMuKBGFDKR1k9A8SCLYaNgXhty", true);

    verifyCancel();

    // Cancel from wizard

    window
      .button(MessageKey.CANCEL.getKey())
      .click();

    // Verify underlying detail screen
    window
      .button(MessageKey.SEND.getKey())
      .requireVisible()
      .requireEnabled();

  }

  /**
   * Verifies that an incorrect Bitcoin format is detected on focus loss
   *
   * @param text    The text to use as a Bitcoin address
   * @param isValid True if the validation should pass
   */
  private void verifyBitcoinAddressField(String text, boolean isValid) {

    // Set the text directly on the combo box editor
    window
      .textBox(MessageKey.RECIPIENT.getKey())
      .setText(text);

    // Lose focus to trigger validation
    window
      .button(MessageKey.PASTE.getKey())
      .focus();

    // Verify the focus change and background color of the editor
    if (isValid) {
      window
        .textBox(MessageKey.RECIPIENT.getKey())
        .background()
        .requireEqualTo(Themes.currentTheme.dataEntryBackground());
    } else {
      window
        .textBox(MessageKey.RECIPIENT.getKey())
        .background()
        .requireEqualTo(Themes.currentTheme.invalidDataEntryBackground());
    }

  }

  /**
   * Verifies that clicking cancel with data present gives a Yes/No popover
   */
  private void verifyCancel() {

    // Click Cancel
    window
      .button(MessageKey.CANCEL.getKey())
      .click();

    // Expect Yes/No popup)
    window
      .button(MessageKey.YES.getKey())
      .requireVisible()
      .requireEnabled();

    window
      .button(MessageKey.CLOSE.getKey())
      .requireVisible()
      .requireEnabled();

    // Click No
    window
      .button(MessageKey.NO.getKey())
      .requireVisible()
      .requireEnabled()
      .click();
  }

}
