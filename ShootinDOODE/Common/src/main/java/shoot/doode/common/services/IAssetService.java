/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoot.doode.common.services;

/**
 *
 * @author tobia
 */
public interface IAssetService {
    
    String[] loadImages();
    String[] unLoadImages();
    String[] loadSounds();
    String[] unLoadSounds();
}
