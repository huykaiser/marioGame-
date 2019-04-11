import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A representation of the mario object as part of the super mario game. A Mario object reacts on the left-, right- and space key. 
 * A space key leads to a jump action, left and right to a horizontal movement depending on the blocks ahead and above.
 * 
 * @author  Purzner, Schwarzmann, Pfeiffer
 * @version V2.0
 */
public class Mario extends Actor {
    
    // instance variables
    private int speed;      // horizontal movement speed
    private int vSpeed;     // vertical movement speed
    private int jumpHeight; // height of a jump

    /** default constructor for a Mario object
     * It´s called for every new Mario on creation
     *
     */
    public Mario(){
        this.speed =5;
        this.jumpHeight =160;
        
    }
    
    public Mario(int speed){
       this.speed = speed;
       this.jumpHeight =125;
    }
    
    
    /**
     * Do whatever Mario does:
     * Checks the keys, does the correct action and falls, if no ground is below Mario
     */
    @Override 
    public void act(){
        actOnKeys ();
        checkIfFalling();
    }    
    
    /**
     * Checks if a key is pressed (left, right, space) and does the correct movement depending on the blocks above 
     * and ahead
     */
    public void actOnKeys () {
        
         if(Greenfoot.isKeyDown("right") && isAheadBlocked() !=1){
             move(this.speed);
             setImage("Mario_right.png");
             
         }
         if(Greenfoot.isKeyDown("left") && isAheadBlocked() !=2){
             move(-this.speed);
             setImage("Mario_left.png");
             
         }
         
         if(Greenfoot.isKeyDown("space") && isOnGround() && !isUpBlocked()){
             this.vSpeed = 0;
             jump();
            
         }   
    }
    
    /**
     * Checks if Mario is falling and stops by setting vspeed to zero
     */
    public void checkIfFalling(){
         if (isAtWorldBottom() || isOnGround()){
             vSpeed=0;
             if(isAtWorldBottom()){
                 Greenfoot.playSound("mario-gameover.WAV");
                 Greenfoot.stop();
             }
         } else{
             vSpeed=2;
             fall();
         }
    }
    /**
     * Jumps Mario one step in sky direction
     */
    public void jump(){
        Greenfoot.playSound("smb_jump.wav");
        moveVertical(-this.jumpHeight);
    }
    
    /**
     * Fall causes one step to the ground
     */
    public void fall(){
         moveVertical(this.vSpeed);      
    }
    
    /**
     * Moves Mario in vertical direction 
     * @param speed the vertical specified distance <br/>
     *              if speed is negative Mario moves up <br/>
     *              if speed is positive Mario moves down<br/>
     */
    public void moveVertical(int speed) {
        this.speed = speed;
        setLocation(getX(), getY() + speed);        
    }
   
    
    /**
     * Checks if Mario is at the World Bottom
     * @return true if Mario is at the Worlds Bottom <br/>
     *         false if Mario is not at the worlds bottom
     */
    public boolean isAtWorldBottom(){
        return getY() >= getWorld().getHeight() - 2;
    }
    
      /**
     * Checks if Mario stands on a block
     * @return true if Mario stands on a block <br/>
     *         false if Mario stands not a block
     */
    public boolean isOnGround(){
        Object item_below=null;
        item_below= getOneObjectAtOffset(0, this.getImage().getHeight()/2,Block.class);
        System.out.println(item_below + " ist unter dir");
        return item_below != null;
    }
    
    /**
     * Checks if the way is blocked
     * @return 0 if the way is not blocked<br/>
     *         1 if the right way is blocked <br/>
     *         2 if the left  way is blocked<br/>
     * 
     */
    public int isAheadBlocked(){
        Object item_Front=null;
        item_Front= getOneObjectAtOffset(this.getImage().getWidth()/2,0,Block.class);
        if(item_Front != null)
            return 1;
        item_Front= getOneObjectAtOffset(-(this.getImage().getWidth()/2),0,Block.class);
        if(item_Front != null)
            return 2;
        return 0;
        //return item_Front != null;
    }
    
    /**
     * Checks if there is a block above Mario
     * @return true if there is a block above Mario
     *         false if there is not a block above Mario
     */
    public boolean isUpBlocked(){        
        Object item_up=null;        
        item_up= getOneObjectAtOffset(0, -(this.getImage().getHeight()/2),Block.class);
        System.out.println(item_up + " ist über dir");
        if(item_up != null ){
            Greenfoot.playSound("mario-pain.WAV");}
        return item_up != null;
        
    }
}


