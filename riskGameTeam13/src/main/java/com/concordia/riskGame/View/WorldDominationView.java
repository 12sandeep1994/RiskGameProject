package com.concordia.riskGame.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import com.concordia.riskGame.model.Continent.Continent;
import com.concordia.riskGame.model.Country.Country;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Player.Player;
import org.apache.commons.lang3.StringUtils;


/**
 * This is the world domination view class created  in observer pattern to display each player stats.
 * @author saich
 *
 */
public class WorldDominationView implements Observer {

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	float noOfCountries= MapContents.getInstance().getCountryAndNeighbors().keySet().size();
	StringBuilder sb1 = new StringBuilder(""); 
	DecimalFormat df = new DecimalFormat();
	@Override
	public void update(Observable arg0, Object arg1) {
		Player player = (Player) arg0;
		if("domination".equals(player.dominationPrint))
		{
			player.dominationPrint="";
			System.out.println(System.lineSeparator()+"############################################### World  domination view ##################################################");
			List<Player> playerList=MapContents.getInstance().getPlayerList();
			DecimalFormat df = new DecimalFormat("#.##");

			for (int i = 0; i < playerList.size(); i++) {
				HashMap<Continent, List<Country>> continentCountries=MapContents.getInstance().getContinentAndItsCountries();
				int continentsOccupied=0;
				List <String> continentsOcuupied= new ArrayList();
				for (Entry<Continent, List<Country>> entry : continentCountries.entrySet()) 
				{
					List<Country> countries=entry.getValue();
					boolean isCountryCaptured=true;
					for(int j=0; j<countries.size();j++)
					{
						List <String> m = new ArrayList<>();
						for (int k=0; k<playerList.get(i).getAssignedCountries().size();k++)
						{
							m.add(playerList.get(i).getAssignedCountries().get(k).getCountryName().toString());
						}

						if(m.contains(countries.get(j).getCountryName()) & isCountryCaptured)

						{
							isCountryCaptured=true;
						}
						else {
							isCountryCaptured=false;

						}

					}

					if(isCountryCaptured)
					{
						continentsOccupied=continentsOccupied+1;
						continentsOcuupied.add(entry.getKey().getContinentName().toString());
					}
					

				}
				String percentage = df.format(((playerList.get(i).getAssignedCountries().size())*100)/noOfCountries);
				List<Country> countryList= playerList.get(i).getAssignedCountries();
				int countryArmies=0;
				for (Country country : countryList) {
					countryArmies=countryArmies+country.getArmies();

				}
				System.out.println(playerList.get(i).getName() +" Percentage of Map Contolled-"+percentage+" Total army player has "+countryArmies+", Countries occupied by the Player are:  "+continentsOccupied + " Occupied Countinents are: " +StringUtils.join(continentsOcuupied, ','));


			}


			System.out.println("################################################################################################################################"+System.lineSeparator());
		}
	}
}
