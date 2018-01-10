interface Store {
    store(message: string);
    retrieveMessages(): string[];
}


class BasicStore implements Store {
   const STORE_LIMIT = 5;

   protected stash: string[] = [];
   protected storeLimit: number = STORE_LIMIT;
  
   store(message: string) {

     if (this.storeLimit === this.stash.length) {
         this.makeMoreRoomForStore();
      }
      this.stash.push(message);
    }
  
    retrieveMessages(): string[] {
      return this.stash;
    }

    makeMoreRoomForStore(): void {
       this.storeLimit += 5;
    }
}

class RotatingStore extends BasicStore {
    makeMoreRoomForStore() {
        this.stash = this.stash.slice(1);
    }
}