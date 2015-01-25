package com.gameconcoillote.ijdkwtd;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;

public class ijdkwtd extends Game implements InputProcessor, ApplicationListener{
	public Screen inGameScreen, menuScreen,gameOver,level2Screen;
	public final static int WIDTH = 1024; //Gdx.graphics.getDesktopDisplayMode().width
	public final static int HEIGHT = 768; //Gdx.graphics.getDesktopDisplayMode().height
    private Music music;

    	
   
    
    @Override
    public void create(){
        
    	inGameScreen = new InGameScreen(this);
        menuScreen = new MenuScreen(this);
        gameOver = new GameOver(this);
        //level2Screen = new Level2Screen(this);
        Gdx.input.setInputProcessor(this);
        setScreen(menuScreen);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
        music.setLooping(false);
        music.setVolume(0.5f);
        music.play();
        
    }
    
    public void addEntity(Entity en)
    {
    	if(getScreen() != null)
    	{
	    	if(getScreen().getClass().getName().compareTo("com.gameconcoillote.ijdkwtd.InGameScreen") == 0)
	    	{
	    		((InGameScreen) getScreen()).addEntity(en);
	    	}
    	}

    }
    

    public String getScreenName(){
        return getScreen().getClass().getName();
    }


    @Override
    public boolean keyDown(int keycode){
        return false;
    }
    @Override
    public boolean keyUp(int keycode){	
        return false;
    }
    @Override
    public boolean keyTyped(char character){
        //System.out.println("Key pressed: " + character);
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        if(getScreen().getClass().getName().compareTo("com.gameconcoillote.ijdkwtd.InGameScreen") == 0){
            if (screenY > 200) {
                ((InGameScreen) this.inGameScreen).getPlayer().move(screenX, screenY);
            }else{
                ((InGameScreen) this.inGameScreen).nextDialog();
            }
        }
        else if (getScreen().getClass().getName().compareTo("com.gameconcoillote.ijdkwtd.MenuScreen") == 0) {
            if (((MenuScreen) getScreen()).menuLang) {
                ((MenuScreen) getScreen()).gotoMenuLangue();
            } else {
                music.dispose();
                setScreen(inGameScreen);
            }
        }else if (getScreen().getClass().getName().compareTo("com.gameconcoillote.ijdkwtd.GameOver") == 0){
            inGameScreen.dispose();
            inGameScreen = new InGameScreen(this);
            setScreen(inGameScreen);
        }
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        if(getScreen().getClass().getName().compareTo("com.gameconcoillote.ijdkwtd.InGameScreen") == 0) {
            ((InGameScreen) this.inGameScreen).getPlayer().changeAnimation(0);
            
            for(Entity e: ((InGameScreen) this.inGameScreen).getEntities())
            {
            	if(e instanceof Item)
            	{
            		if(((Item) e).collideWith(screenX, screenY))
            		{
            			System.out.println((((InGameScreen)getScreen()).getPlayer().box.x) - (e.box.x));
            			if(Math.abs(  
            					(((InGameScreen)getScreen()).getPlayer().box.x) - (e.box.x)
            					) < ((Item) e).getActivationDistance())
            			{
            				((Item) e).activate();
            			}
            		}
            	}
            }
        }
        
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY){
        return false;
    }
    @Override
    public boolean scrolled(int amount){
        return false;
    }
}
