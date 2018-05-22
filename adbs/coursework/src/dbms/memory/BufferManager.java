package dbms.memory;

import java.util.Arrays;

/**
 * Created by s1443483 on 12/03/18.
 */
public class BufferManager extends AbstractBufferManager{

    public BufferManager(Policy replPolicy, int numFrames, int pageSize, DiskSpaceManager spaceMan){
        super(replPolicy,numFrames, pageSize, spaceMan);
    }

    public int fetch(int pageAddr) throws FullBufferException{
        byte[] page;
        byte[] pageBuff;
        switch (replPolicy){
            case MRU:
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    // Check to see if the page exists in the buffer pool already
                    if (this.bookkeeping.get(i).pageAddress == pageAddr) {

                        this.bookkeeping.get(i).pinCount++;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j>0;j--){
                            this.bookkeeping.set(j,this.bookkeeping.get(j-1));
                         }

                        this.bookkeeping.set(0,tempFrame);
                        return this.bookkeeping.get(0).frameAddress;

                     }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).isEmptyFrame()) {

                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount++;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j>0;j--){
                            this.bookkeeping.set(j,this.bookkeeping.get(j-1));
                        }

                        this.bookkeeping.set(0,tempFrame);
                        return this.bookkeeping.get(0).frameAddress;

                    }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).pinCount == 0){

                        if(this.bookkeeping.get(i).dirty){
                            pageBuff = readFromBuffer(this.bookkeeping.get(i).frameAddress, pageSize);
                            spaceManager.write(this.bookkeeping.get(i).pageAddress,pageBuff);
                        }
                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount = 1;
                        this.bookkeeping.get(i).dirty= false;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j>0;j--){
                            this.bookkeeping.set(j,this.bookkeeping.get(j-1));
                        }

                        this.bookkeeping.set(0,tempFrame);
                        return this.bookkeeping.get(0).frameAddress;

                    }
                }
            case LRU:
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    // Check to see if the page exists in the buffer pool already
                    if (this.bookkeeping.get(i).pageAddress == pageAddr) {

                        this.bookkeeping.get(i).pinCount++;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j<this.bookkeeping.size()-1;j++){
                            this.bookkeeping.set(j,this.bookkeeping.get(j+1));
                        }

                        this.bookkeeping.set(this.bookkeeping.size()-1,tempFrame);
                        return this.bookkeeping.get(this.bookkeeping.size()-1).frameAddress;

                    }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).isEmptyFrame()) {

                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount++;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j<this.bookkeeping.size()-1;j++){
                            this.bookkeeping.set(j,this.bookkeeping.get(j+1));
                        }

                        this.bookkeeping.set(this.bookkeeping.size()-1,tempFrame);
                        return this.bookkeeping.get(this.bookkeeping.size()-1).frameAddress;

                    }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).pinCount == 0){

                        if(this.bookkeeping.get(i).dirty){
                            pageBuff = readFromBuffer(this.bookkeeping.get(i).frameAddress, pageSize);
                            spaceManager.write(this.bookkeeping.get(i).pageAddress,pageBuff);
                        }
                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount = 1;
                        this.bookkeeping.get(i).dirty= false;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j<this.bookkeeping.size()-1;j++){
                            this.bookkeeping.set(j,this.bookkeeping.get(j+1));
                        }

                        this.bookkeeping.set(this.bookkeeping.size()-1,tempFrame);
                        return this.bookkeeping.get(this.bookkeeping.size()-1).frameAddress;

                    }
                }
            case FIFO:
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    // Check to see if the page exists in the buffer pool already
                    if (this.bookkeeping.get(i).pageAddress == pageAddr) {
                        this.bookkeeping.get(i).pinCount++;
                        return this.bookkeeping.get(i).frameAddress;
                    }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).isEmptyFrame()) {

                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount++;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j<this.bookkeeping.size()-1;j++){
                            this.bookkeeping.set(j,this.bookkeeping.get(j+1));
                        }

                        this.bookkeeping.set(this.bookkeeping.size()-1,tempFrame);
                        return this.bookkeeping.get(this.bookkeeping.size()-1).frameAddress;

                    }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).pinCount == 0){

                        if(this.bookkeeping.get(i).dirty){
                            pageBuff = readFromBuffer(this.bookkeeping.get(i).frameAddress, pageSize);
                            spaceManager.write(this.bookkeeping.get(i).pageAddress,pageBuff);
                        }

                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount = 1;
                        this.bookkeeping.get(i).dirty= false;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j<this.bookkeeping.size()-1;j++){
                            this.bookkeeping.set(j,this.bookkeeping.get(j+1));
                        }

                        this.bookkeeping.set(this.bookkeeping.size()-1,tempFrame);
                        return this.bookkeeping.get(this.bookkeeping.size()-1).frameAddress;

                    }
                }
            case LIFO:
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    // Check to see if the page exists in the buffer pool already
                    if (this.bookkeeping.get(i).pageAddress == pageAddr) {
                        this.bookkeeping.get(i).pinCount++;
                        return this.bookkeeping.get(i).frameAddress;
                    }
                }
                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).isEmptyFrame()) {

                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount++;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j>0;j--){
                            this.bookkeeping.set(j,this.bookkeeping.get(j-1));
                        }
                        this.bookkeeping.set(0,tempFrame);
                        return this.bookkeeping.get(0).frameAddress;
                    }
                }

                for (int i = 0;i<this.bookkeeping.size();i++) {

                    if (this.bookkeeping.get(i).pinCount == 0){
                        if(this.bookkeeping.get(i).dirty){
//                            System.out.println("Dirty bit");
                            pageBuff = readFromBuffer(this.bookkeeping.get(i).frameAddress, pageSize);
                            spaceManager.write(this.bookkeeping.get(i).pageAddress,pageBuff);
                        }

                        page = this.spaceManager.read(pageAddr, pageSize);
                        writeToBuffer(this.bookkeeping.get(i).frameAddress,page);
                        this.bookkeeping.get(i).pageAddress = pageAddr;
                        this.bookkeeping.get(i).pinCount = 1;
                        this.bookkeeping.get(i).dirty= false;
                        FrameInfo tempFrame = this.bookkeeping.get(i);

                        // Re-order the frames according to the policy / Replacement policy
                        for(int j = i;j>0;j--){
                            this.bookkeeping.set(j,this.bookkeeping.get(j-1));
                        }

                        this.bookkeeping.set(0,tempFrame);
                        return this.bookkeeping.get(0).frameAddress;

                    }
                }
        }
        throw new FullBufferException();
    }

    public void release(int frameAddr, boolean modified) {

        for(int i=0;i<this.bookkeeping.size();i++) {
            if (this.bookkeeping.get(i).frameAddress == frameAddr) {
                if(modified) {
                    this.bookkeeping.get(i).dirty = modified;
                }
                this.bookkeeping.get(i).pinCount--;
            }
        }

    }

    // Method that reads a page from the buffer pool
    public byte[] readFromBuffer(int addr, int length) {
        return Arrays.copyOfRange(this.bufferPool, addr, addr+length);
    }

    // Method that writes a page to the buffer pool
    public void writeToBuffer(int addr, byte[] page) {
        for ( int i=0; i < page.length; i++ ) {
            this.bufferPool[addr+i] = page[i];
        }
    }
}
