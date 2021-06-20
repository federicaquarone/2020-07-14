package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void listAllTeams(Map<Integer, Team> idMap){
		String sql = "SELECT * FROM Teams";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!idMap.containsKey(res.getInt("TeamID"))) {
				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				idMap.put(team.getTeamID(),team);
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public List<Adiacenza> getArchi(Map<Integer,Team> idMap) {
		String sql="SELECT t1.TeamID, t2.TeamID, m.ResultOfTeamHome as r "
				+ "FROM teams t1, teams t2, matches m "
				+ "WHERE t1.TeamID>t2.TeamID "
				+ "AND t1.TeamID=m.TeamHomeID AND t2.TeamID=m.TeamAwayID "
				+ "AND m.ResultOfTeamHome >0";
		
		List<Adiacenza> result= new LinkedList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int pesoT1=0;
				int pesoT2=0;
                if(res.getInt("r")>0) {
                	//vince squadra in casa
                	pesoT1 +=3;
                	pesoT2 +=0;
                }
                if(res.getInt("r")==0) {
                	pesoT1+=1;
                	pesoT2+=1;
                	
                }
                if(res.getInt("r")<0) {
                	pesoT1+=0;
                	pesoT2+=3;
                }
				 int delta= pesoT1-pesoT2;
				 result.add(new Adiacenza(idMap.get(res.getInt("t1.TeamID")),idMap.get(res.getInt("t2.TeamID")) , delta));
				 

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}*/

	public List<Team> getVertici(Map<Integer, Team> idMap) {
		String sql="select DISTINCT t.TeamID  "
				+ "FROM teams t ";
		List<Team> result= new LinkedList<Team>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.containsKey(res.getInt("t.TeamID"))) { //OCCHIO QUA!!
					result.add(idMap.get(res.getInt("t.TeamID")));
				}
			
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
