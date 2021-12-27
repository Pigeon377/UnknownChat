package chat

import (
	"Thyme/web/extension"
	"github.com/gin-gonic/gin"
	"strconv"
)

func JoinRoom(ctx *gin.Context) {
	uuid := extension.InterceptWithToken(ctx)
	if uuid == -1 {
		return
	}
	roomIDString := ctx.PostForm("room_id")
	roomID, err := strconv.Atoi(roomIDString)
	if err != nil {
		ctx.JSON(200, gin.H{
			"status":  0,
			"message": "InvalidArgument",
			"data":    gin.H{},
		})
		return
	}
	res := extension.SendJoinRoomMessageWithGrpc(uuid, int64(roomID))
	if res.Status {
		ctx.JSON(200, gin.H{
			"status":  1,
			"message": "Succeed",
			"data": gin.H{
				"user_list":     res.UserList,
				"websocket_url": res.RoomWebsocketURL,
				"room_name":     res.RoomName,
			},
		})
	}else{
		ctx.JSON(200, gin.H{
			"status":  0,
			"message": "JoinFailed",
			"data": gin.H{},
		})
	}
}
