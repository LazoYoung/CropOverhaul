package com.naver.jupiter1390;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
	
	private Plugin plugin;
	
	public Events(Plugin plugin) {
		
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onCropGrow(BlockGrowEvent event) {
		
		FileConfiguration config = plugin.getConfig();
		Block block = event.getBlock();
		Material mat = block.getType(); // 이벤트가 발생된 블럭의 아이템코드 (Material)
		
		// config의 Types 설정 섹션
		ConfigurationSection cs = config.getConfigurationSection("Types");
		
		/* config의 Types. 아래의 모든 키값
		 * 
		 * 예시)
		 * Types:
		 *   A: 1
		 *   B: 2
		 *   C: 3
		 *   
		 *   여기서 Types 아래의 키값: A, B, C
		 *   
		 *   cs.getKeys(bool) 에서 bool은
		 *   Types의 A, B, C 각각에 대입된 아래 깊숙한 값(1, 2, 3)까지도 다 구해올것인지
		 *   말것인지에 대한 결정
		 */
		Set<String> types = cs.getKeys(false);
		
		// Types 아래의 모든 키값(A, B, C)을 for루프로 각각 t에 대입
		for(String t : types) {
			
			/* 키값을 Material Enum값으로 변환
			 * 여기서 Material Enum이란 Material은 마크상의
			 * 모든 아이템이나 블럭의 아이템코드를 뜻함
			 * 이게 Enum 이라는것으로 정의되어 있음
			 * 자세한건 Material 을 직접 확인해보기
			 * 
			 * 여기서 각각의 정의된 글이 Material들임.
			 * (자세한건 Java Enum 공부)
			 * ㅇㅋ 알겠음
			 * 근데 그 double c1의 값 범위가 뭐임
			 */
			Material type = Material.getMaterial(t);
			
			// 이벤트가 발생된 블럭의 재질이 config.yml에 정의되어있는
			// 재질에 포함되어 있을떄
			//아 그런거군요 근데 그냥 Sugaecane으로 연결시키려면 어떻게 코드 짜야되나요
			//이렇게요? -> mat이 sugar_cane_blocks일시 sugarcane으로 연결시킨다
			/*
			 * 구지 그래야하는 이유는 모르겠지만 그러면 각각의 재질들을
			 * 따로 이름(alias)로 플러그인에 저장시켜야되요.
			 * 이렇게 하는 이유가 사탕수수는 괜찮은데
			 * 그 밀이나 호박같은경우  CROPS 하나로 통일되고 코드만 달라서
			 * 나중에 조작하기가 힘들어져요 아 유저들이 쉽게 알아보게ㅇㅇ 저도 알아봐야되고
			 * 그럼 해드리죠. 예시를 적어줄게요 일단 여기서말고 제 컴터로 할테니
			 * 아까 배웠던 풀 하세요.
			 */
			if(mat.equals(type)) {
				
				double c = config.getInt("Types." + t);
				double c1 = Math.random();
				
				if(c1 > c) {
					
					Location loc = block.getLocation();
					
					// DEBUG 디버그 메세지
					plugin.getLogger().info("CropGrowEvent on world " + loc.getWorld()
							+ ", X" + loc.getBlockX() + ", Y" + loc.getBlockY()+", Z" + loc.getBlockZ());
					
					// 아래블럭 감지 루프 (목표위치 아래블럭부터 그 3블럭 아래까지)
					for(int i=1;i<4;i++) {
						
						Block b = loc.getWorld().getBlockAt(loc.add(0, -i, 0));
						
						if(!b.getType().equals(type)) {
							
							// 가장 밑둥의 사탕수수를 변경
							loc.add(0, 1, 0).getBlock().setType(Material.DEAD_BUSH);
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}