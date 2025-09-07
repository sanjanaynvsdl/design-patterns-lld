package AbstractFactory;

public interface IGameFactory {
    IstoneFactory getStoneFactory;
    IBackgroundFactory getBackgroundFactory;
    ICharacterFactory getCharaterFactory;
    
    
}
