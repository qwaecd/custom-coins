# Custom Coins
## 功能特性
- 🪙 可配置的定制硬币
- ✅ 白名单玩家验证机制
- 🔐 NBT数据加密验证（包含玩家名数据）
- ⚙️ 基于Forge配置系统的动态配置

## 使用说明

### 物品交互
1. 手持硬币右键单击任意方块
2. 系统会检查玩家是否在`whiteListPlayerName`白名单中
3. 注入灵魂的coin将获得：
   - 无限附魔
   - 物品描述（Lore）
   - 唯一性验证标记（NBT: check=true）

### 配置指南
编辑 `config/customcoins-common.toml`:
```toml
[General]
# 允许使用硬币的玩家名称列表
whiteListPlayerName = ["qwaecd", "JohnXue"]
