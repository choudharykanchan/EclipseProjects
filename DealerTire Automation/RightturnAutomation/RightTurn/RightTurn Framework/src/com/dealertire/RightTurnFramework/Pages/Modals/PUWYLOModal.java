package com.dealertire.RightTurnFramework.Pages.Modals;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dealertire.RightTurnFramework.PageObject;
import com.dealertire.RightTurnFramework.RTPage;
import com.dealertire.RightTurnFramework.RTPageFactory;
import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Size;

/**
 * The PUWYLO modal, which can appear on a number of pages.
 * @author bgreen
 *
 */
public class PUWYLOModal extends RTPage {

	/*puwlyo*/
	private WebElement puwyloModal;
	private WebElement puwyloSection;
	
	@FindBy(linkText="Yes")
	private WebElement puwyloYesButton;
	
	//@FindBy(linkText="I would rather start over")
	private WebElement puwyloStartOver;
	
	@FindBy(css="#RT_Modal .close")
	private WebElement puwyloCloseButton;

	@FindBy(css="#puwyloSection + div")
	private WebElement puwyloMake;
	
	@FindBy(css="#puwyloSection + div + div")
	private WebElement puwyloModel;
	
	@FindBy(css="div.puwyloRow > div > p > strong")
	private WebElement serviceDescrip;
	
	
	@FindBy(css="div.puwyloRow > div > h1")
	private WebElement sizeElem;
	
	/**
	 * Construct a new modal
	 * @param driver The driver, for interacting with the modal
	 */
	public PUWYLOModal(WebDriver driver) {
		super(driver);
	}

	/**
	 * Wait for the PUWYLO modal to appear. 
	 */
	public void waitForModal() {
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.visibilityOf(puwyloModal));
		Utils.WaitForAllAnimationComplete(driver);
	}

	
	@Override
	public boolean isLoaded() {
		try {
			return puwyloModal.isDisplayed();
		} catch (NoSuchElementException e) {
			return false; //If the modal does not exist, it is not displayed
		}
	}

	@Override
	public void waitForPageLoad() {
		waitForModal();
	}
	
	/**
	 * Get the section displayed in the PUWYLO modal.
	 * @return The section it wants to take you to if you click yes.
	 */
	public String getSection() {
		return this.puwyloSection.getText();
	}
	
	/**
	 * Get the make of the vehicle in the PUWYLO modal
	 * @return The make of the vehicle from the saved session
	 */
	public String getMake() {
		return this.puwyloMake.getText();
	}
	
	/**
	 * Get the model of the vehicle in the PUWYLO modal.
	 * Note: This is the modEL, not the modAl. Mod-el, not Mode-al.
	 * @return The model of the vehicle from the saved session
	 */
	public String getModel() {
		return this.puwyloModel.getText();
	}
	
	/**
	 * Get the size displayed in the PUWYLO modal.
	 * @return The size from the saved session
	 */
	public Size getSize() {
		Size size = new Size(sizeElem.getText());
		return size;
	}
	
	/**
	 * Click "Yes" on the PUWYLO modal
	 * @return The page it takes you to.
	 */
	public PageObject clickYes() {
		return navigate(puwyloYesButton);
	}

	/**
	 * Click "No" on the PUWYLO modal.
	 * @return The current page, since it should not navigate.
	 */
	public PageObject clickNo() {
		puwyloStartOver.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*3).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#RT_Modal")));
		return RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
	}
	
	/**
	 * Click "Close" on the PUWYLO modal
	 * @return This page, since it should not navigate.
	 */
	public PageObject clickClose() {
		puwyloCloseButton.click();
		new WebDriverWait(driver, RTPage.DEFAULT_TIMEOUT*6).until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#RT_Modal"))));
		return RTPageFactory.getPageFromURL(driver.getCurrentUrl(), driver);
	}

}
